package br.dev.viniciusleonel.localweb.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class SpamControlService {
    private val sentEmails = mutableMapOf<String, MutableList<LocalDateTime>>()
    private val emailLimit = 5 // Limite de e-mails
    private val timeFrame = 1L // Em min

    fun canSendEmail(userEmail: String): Boolean {
        val now = LocalDateTime.now()
        val emailTimes = sentEmails.getOrDefault(userEmail, mutableListOf())

        emailTimes.removeIf { it.isBefore(now.minus(timeFrame, ChronoUnit.MINUTES)) }

        return if (emailTimes.size < emailLimit) {
            emailTimes.add(now)
            sentEmails[userEmail] = emailTimes
            true
        } else {
            false
        }
    }
}
