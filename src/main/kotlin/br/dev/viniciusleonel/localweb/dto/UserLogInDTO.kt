package br.dev.viniciusleonel.localweb.dto

import jakarta.validation.constraints.NotBlank

class UserLogInDTO(

    @field:NotBlank(message = "Username is mandatory")
    var username: String = "",

    @field:NotBlank(message = "Password is mandatory")
    var password: String = "",
)