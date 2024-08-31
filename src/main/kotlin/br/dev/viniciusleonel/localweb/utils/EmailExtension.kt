package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.email.*
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
            sendByUser = email.sentByUser.email,
            receiveByUser = email.receivedByUser.email,
            subject = email.subject,
            sendAt = email.sentAt,
            wasRead = email.wasRead
        )
    }
}

fun Email.toEmailDetailsDTO(): EmailDetailsWithBodyDTO {
    return EmailDetailsWithBodyDTO(
        id = this.id,
        sendByUser = this.sentByUser.email,
        receiveByUser = this.receivedByUser.email,
        subject = this.subject,
        body = this.body,
        sendAt = this.sentAt,
        wasRead = this.wasRead
    )
}

fun Email.toSentEmailDTO(): SentEmailDTO {
    return SentEmailDTO(
        id = this.id,
        subject = this.subject,
        sendTo = this.receivedByUser.email,
        sentAt = this.sentAt,
        wasRead = this.wasRead

    )
}

fun Email.toReceivedEmailDTO(): ReceivedEmailDTO {
    return ReceivedEmailDTO(
        id = this.id,
        subject = this.subject,
        receivedFrom = this.sentByUser.email,
        sentAt = this.sentAt,
        wasRead = this.wasRead
    )
}


