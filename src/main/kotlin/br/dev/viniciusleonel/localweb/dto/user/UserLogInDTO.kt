package br.dev.viniciusleonel.localweb.dto.user

import jakarta.validation.constraints.NotBlank

class UserLogInDTO(

    @field:NotBlank(message = "Username is required")
    var username: String = "",

    @field:NotBlank(message = "Password is required")
    var password: String = "",
)