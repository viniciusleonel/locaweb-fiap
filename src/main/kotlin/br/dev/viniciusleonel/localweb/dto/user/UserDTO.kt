package br.dev.viniciusleonel.localweb.dto.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserDTO(

    @field:NotBlank(message = "Name is required")
    var name: String = "",

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    var email: String = "",

    @field:NotBlank(message = "Username is required")
    var username: String = "",

    @field:NotBlank(message = "Password is required")
    var password: String = "",
)
