package br.dev.viniciusleonel.localweb.dto.preferences

data class UserUpdatePreferencesDTO(
    val theme: String? = null,
    val colorScheme: String? = null,
    val categories: String? = null,
    val labels: String? = null
)
