package br.dev.viniciusleonel.localweb.dto

import jakarta.validation.constraints.NotBlank

class UserLogOutDTO(

    @field:NotBlank(message = "Username is mandatory")
    var username: String = "",

)