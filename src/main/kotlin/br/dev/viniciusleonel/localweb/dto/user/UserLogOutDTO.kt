package br.dev.viniciusleonel.localweb.dto.user

import jakarta.validation.constraints.NotBlank

class UserLogOutDTO(

    @field:NotBlank(message = "Username is required")
    var username: String = "",

)