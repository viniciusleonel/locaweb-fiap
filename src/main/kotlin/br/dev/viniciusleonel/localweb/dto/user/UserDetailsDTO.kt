package br.dev.viniciusleonel.localweb.dto.user

import br.dev.viniciusleonel.localweb.model.UserPreferences

class UserDetailsDTO(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val isLoggedIn: Boolean,
    val userPreferences: UserPreferences
)