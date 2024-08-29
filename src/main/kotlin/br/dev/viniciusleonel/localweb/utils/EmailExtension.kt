package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.email.EmailDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDetailsDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDetailsWithBodyDTO
import br.dev.viniciusleonel.localweb.model.Email
import br.dev.viniciusleonel.localweb.model.User
import org.springframework.data.domain.Page

fun EmailDTO.generateEmailWithSenderAndRecipient(sender: User, recipient: User): Email {
    return Email(
        sentByUser = sender,
        receivedByUser = recipient,
        subject = this.subject,
        body = this.body
    )
}

fun EmailDTO.toEmail(email: Email): Email {
    return Email(
        sentByUser = email.sentByUser,
        receivedByUser = email.receivedByUser,
        subject = email.subject,
        body = email.body
    )
}

fun Page<Email>.toEmailDetailsDTO(): Page<EmailDetailsDTO> {
    return this.map { email ->
        EmailDetailsDTO(
            id = email.id,
            sendByUser = email.sentByUser.username, // Ajuste conforme necessário
            receiveByUser = email.receivedByUser?.username ?: "N/A", // Ajuste conforme necessário
            subject = email.subject,
            sendAt = email.sentAt,
            wasRead = email.wasRead
        )
    }
}

fun Email.toEmailDetailsDTO(): EmailDetailsWithBodyDTO {
    return EmailDetailsWithBodyDTO(
        id = this.id,
        sendByUser = this.sentByUser.username, // Ajuste conforme necessário
        receiveByUser = this.receivedByUser?.username ?: "N/A", // Ajuste conforme necessário
        subject = this.subject,
        body = this.body,
        sendAt = this.sentAt,
        wasRead = this.wasRead
    )
}


