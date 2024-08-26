package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.model.Email
import org.springframework.stereotype.Service

//@Service
//class EmailService(private val spamControlService: SpamControlService) {
//    private val emails = mutableListOf<Email>()
//
//    fun listEmails(): List<Email> = emails
//
//    fun sendEmail(email: Email): Email {
//        if (!spamControlService.canSendEmail(email.from)) {
//            throw IllegalArgumentException("Spam detected! Cannot send email.")
//        }
//        spamControlService.recordEmailSent(email.from)
//        emails.add(email)
//        return email
//    }
//
//    fun getEmailDetails(id: Long): Email? {
//        return emails.find { it.id == id }
//    }
//}
