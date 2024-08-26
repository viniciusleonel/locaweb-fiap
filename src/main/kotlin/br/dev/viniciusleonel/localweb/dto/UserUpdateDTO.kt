package br.dev.viniciusleonel.localweb.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserUpdateDTO(

    @field:Email(message = "Invalid email format")
    val email: String? = null,
    val username: String? = null,
    val password: String? = null
)

