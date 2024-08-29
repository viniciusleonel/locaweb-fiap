package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.email.EmailDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDetailsDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDetailsWithBodyDTO
import br.dev.viniciusleonel.localweb.model.Email
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.repository.EmailRepository
import br.dev.viniciusleonel.localweb.repository.UserRepository
import br.dev.viniciusleonel.localweb.utils.generateEmailWithSenderAndRecipient
import br.dev.viniciusleonel.localweb.utils.toEmailDetailsDTO
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmailService(
    private val emailRepository: EmailRepository,
    private val userRepository: UserRepository
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
    fun sendEmail(emailDTO: EmailDTO): Email {
        val sender  = userRepository.findByEmail(emailDTO.sentByUser)
            ?: throw EntityNotFoundException("User not found with id: '${emailDTO.sentByUser}'")

        val recipient   = userRepository.findByEmail(emailDTO.receivedByUser)
            ?: throw EntityNotFoundException("User not found with id: '${emailDTO.receivedByUser}'")

        val email = emailDTO.generateEmailWithSenderAndRecipient(sender, recipient)
        emailRepository.save(email)

        redirectEmails(sender, email, recipient)

        return email
    }

    fun listEmails(pageable: Pageable): Page<EmailDetailsDTO> {
        val page = emailRepository.findAll(pageable)
        return page.toEmailDetailsDTO()
    }

    @Transactional
    fun deleteEmailById(id: Long) {
        val email = emailRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Email not found with id: '$id'") }
        emailRepository.delete(email)
    }

    fun getEmailById(id: Long): EmailDetailsWithBodyDTO {
        val email = emailRepository.findById(id).orElseThrow { EntityNotFoundException("Email not found with id: '$id'") }
        return email.toEmailDetailsDTO()
    }

}