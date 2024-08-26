package br.dev.viniciusleonel.localweb.dto

data class UserUpdateDTO(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null
)

