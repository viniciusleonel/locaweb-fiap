package br.dev.viniciusleonel.localweb.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserDTO(

    @field:NotBlank(message = "Name is mandatory")
    var name: String = "",

    @field:NotBlank(message = "Email is mandatory")
    @field:Email(message = "Invalid email format")
    var email: String = "",

    @field:NotBlank(message = "Username is mandatory")
    var username: String = "",

    @field:NotBlank(message = "Password is mandatory")
    var password: String = "",
)
