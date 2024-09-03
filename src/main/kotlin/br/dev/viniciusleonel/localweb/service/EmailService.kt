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
import br.dev.viniciusleonel.localweb.utils.*
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

        sender.isActive(userRepository, sender)
        if (!recipient.status)
            throw CustomException("User not found: '${recipient.email}'", HttpStatus.NOT_FOUND)

        // Verificar se o usuário pode enviar mais e-mails
        if (!spamControlService.canSendEmail(sender.email))
            throw CustomException("Email sending limit reached for user: '${sender.email}'", HttpStatus.TOO_MANY_REQUESTS)

        val email = emailDTO.generateEmailWithSenderAndRecipient(sender, recipient)
        emailRepository.save(email)

        redirectEmails(sender, email, recipient)

        return email.toSentEmailDTO()
    }

    // Analisar necessidade
    fun listEmails(pageable: Pageable): Page<EmailDetailsDTO> {
        val page = emailRepository.findAll(pageable)
        return page.toEmailDetailsDTO()
    }

    fun listEmailsBySender(id: Long, pageable: Pageable): Page<EmailDetailsDTO> {
        val page = emailRepository.findAllBySentByUserId(id, pageable)
        val sender = userRepository.findById(id).orElseThrow { EntityNotFoundException("User not found with id: $id") }
        sender.isActive(userRepository, sender)
        return page.toEmailDetailsDTO()
    }

    fun listEmailsByReceiver(id: Long, pageable: Pageable): Page<EmailDetailsDTO> {
        val page = emailRepository.findAllByReceivedByUserId(id, pageable)
        val receiver = userRepository.findById(id).orElseThrow { EntityNotFoundException("User not found with id: $id") }
        receiver.isActive(userRepository, receiver)
        return page.toEmailDetailsDTO()
    }

    @Transactional
    fun deleteEmailById(id: Long) : MessageDTO{
        val email = emailRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Email not found with id: '$id'") }
        email.sentByUser.isActive(userRepository, email.sentByUser)
        emailRepository.delete(email)
        return MessageDTO("Email with id: '$id' was deleted")
    }

    @Transactional
    fun getEmailById(id: Long): EmailDetailsWithBodyDTO {
        val email = emailRepository.findById(id).orElseThrow { EntityNotFoundException("Email not found with id: '$id'") }
        email.sentByUser.isActive(userRepository, email.sentByUser)
        email.wasRead = true
        emailRepository.save(email)
        return email.toEmailDetailsDTO()
    }

}