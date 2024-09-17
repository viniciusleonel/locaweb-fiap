package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.MessageDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDetailsDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDetailsWithBodyDTO
import br.dev.viniciusleonel.localweb.dto.email.SentEmailDTO
import br.dev.viniciusleonel.localweb.infra.exception.CustomException
import br.dev.viniciusleonel.localweb.model.Email
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.repository.EmailRepository
import br.dev.viniciusleonel.localweb.repository.UserRepository
import br.dev.viniciusleonel.localweb.utils.generateEmailWithSenderAndRecipient
import br.dev.viniciusleonel.localweb.utils.isActive
import br.dev.viniciusleonel.localweb.utils.toEmailDetailsDTO
import br.dev.viniciusleonel.localweb.utils.toSentEmailDTO
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmailService(
    private val emailRepository: EmailRepository,
    private val userRepository: UserRepository,
    private val spamControlService: SpamControlService
) {

    private fun redirectEmails(
        sender: User,
        email: Email,
        recipient: User
    ) {
        sender.sentEmails.add(email)
        userRepository.save(sender)

        recipient.receivedEmails.add(email)
        userRepository.save(recipient)
    }

    @Transactional
    fun sendEmail(emailDTO: EmailDTO): SentEmailDTO {
        val sender  = userRepository.findByEmail(emailDTO.sentByUser)
            ?: throw EntityNotFoundException("User not found with email: '${emailDTO.sentByUser}'")

        val recipient   = userRepository.findByEmail(emailDTO.receivedByUser)
            ?: throw EntityNotFoundException("User not found with email: '${emailDTO.receivedByUser}'")

        sender.isActive(userRepository)
        if (!recipient.status)
            throw CustomException("User not found: '${recipient.email}'", HttpStatus.NOT_FOUND)

        if (!spamControlService.canSendEmail(sender.email))
            throw CustomException("Email sending limit reached for user: '${sender.email}'", HttpStatus.TOO_MANY_REQUESTS)

        val email = emailDTO.generateEmailWithSenderAndRecipient(sender, recipient)
        emailRepository.save(email)

        redirectEmails(sender, email, recipient)

        return email.toSentEmailDTO()
    }

    fun listReceivedEmailsByUserId(id: Long, pageable: Pageable): Page<EmailDetailsDTO> {
        val page = emailRepository.findAllBySentByUserId(id, pageable)
        val sender = userRepository.findById(id).orElseThrow { EntityNotFoundException("User not found with id: $id") }
        sender.isActive(userRepository)
        return page.toEmailDetailsDTO()
    }

    fun listSentEmailsByUserId(id: Long, pageable: Pageable): Page<EmailDetailsDTO> {
        val page = emailRepository.findAllByReceivedByUserId(id, pageable)
        val sender = userRepository.findById(id).orElseThrow { EntityNotFoundException("User not found with id: $id") }
        sender.isActive(userRepository)
        return page.toEmailDetailsDTO()
    }

    @Transactional
    fun deleteEmailById(id: Long) : MessageDTO{
        val email = emailRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Email not found with id: '$id'") }
        email.sentByUser.isActive(userRepository)
        emailRepository.delete(email)
        return MessageDTO("Email with id: '$id' was deleted")
    }

    @Transactional
    fun getEmailById(id: Long): EmailDetailsWithBodyDTO {
        val email = emailRepository.findById(id).orElseThrow { EntityNotFoundException("Email not found with id: '$id'") }
        email.sentByUser.isActive(userRepository)
        email.wasRead = true
        emailRepository.save(email)
        return email.toEmailDetailsDTO()
    }

}