package br.dev.viniciusleonel.localweb.model

import java.time.LocalDateTime

data class Email(
    val id: Long,
    val from: String,
    val to: String,
    val subject: String,
    val body: String,
    val sentAt: LocalDateTime = LocalDateTime.now()
)

