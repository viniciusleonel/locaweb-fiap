package br.dev.viniciusleonel.localweb.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SpamControlService {
    private val sentEmails = mutableMapOf<String, MutableList<LocalDateTime>>()

    fun canSendEmail(userId: String): Boolean {
        val now = LocalDateTime.now()
        val emails = sentEmails[userId] ?: mutableListOf()
        emails.removeIf { it.isBefore(now.minusMinutes(5)) } // Remove e-mails mais antigos que 5 minutos
        return emails.size < 10 // Limite de 10 e-mails a cada 5 minutos
    }

    fun recordEmailSent(userId: String) {
        val emails = sentEmails.getOrPut(userId) { mutableListOf() }
        emails.add(LocalDateTime.now())
    }
}
