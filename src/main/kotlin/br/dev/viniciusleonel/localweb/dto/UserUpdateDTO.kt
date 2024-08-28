package br.dev.viniciusleonel.localweb.dto

import jakarta.validation.constraints.Email

data class UserUpdateDTO(

    val name: String? = null,
    @field:Email(message = "Invalid email format")
    val email: String? = null,
    val username: String? = null,
    val password: String? = null
)

