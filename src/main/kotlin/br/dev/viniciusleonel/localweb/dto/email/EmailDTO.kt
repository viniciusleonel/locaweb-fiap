package br.dev.viniciusleonel.localweb.dto.email

import jakarta.validation.constraints.NotBlank

data class EmailDTO(

    @field:NotBlank(message = "Sender's email is required.")
    var sentByUser: String,

    @field:NotBlank(message = "Recipient's email is required.")
    var receivedByUser: String,

    @field:NotBlank(message = "Subject is required.")
    var subject: String,

    @field:NotBlank(message = "Email body is required.")
    var body: String,
)
