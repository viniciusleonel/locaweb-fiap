package br.dev.viniciusleonel.localweb.dto.email

import java.time.LocalDateTime

class SentEmailDTO(
    val id: Long,
    val subject: String,
    val sendTo: String,
    val sentAt: LocalDateTime,
    val wasRead: Boolean
)