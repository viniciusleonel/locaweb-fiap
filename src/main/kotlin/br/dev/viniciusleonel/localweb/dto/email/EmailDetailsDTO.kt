package br.dev.viniciusleonel.localweb.dto.email

import java.time.LocalDateTime

data class EmailDetailsDTO(
    val id: Long,
    val sendByUser: String,
    val receiveByUser: String,
    val subject: String,
    val sendAt: LocalDateTime,
    val wasRead: Boolean
)
