package br.dev.viniciusleonel.localweb.dto.email

import java.time.LocalDateTime

data class EmailDetailsWithBodyDTO(
    val id: Long,
    val sendByUser: String,
    val receiveByUser: String,
    val subject: String,
    val body: String,
    val sendAt: LocalDateTime,
    val wasRead: Boolean
)
