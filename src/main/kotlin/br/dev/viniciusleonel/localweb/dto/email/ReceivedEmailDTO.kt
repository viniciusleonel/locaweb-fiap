package br.dev.viniciusleonel.localweb.dto.email

import java.time.LocalDateTime

class ReceivedEmailDTO(
    val id: Long,
    val subject: String,
    val receivedFrom: String,
    val sentAt: LocalDateTime,
    val wasRead: Boolean
)