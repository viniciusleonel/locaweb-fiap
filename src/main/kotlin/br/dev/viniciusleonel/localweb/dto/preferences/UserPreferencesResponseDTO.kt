package br.dev.viniciusleonel.localweb.dto.preferences

data class UserPreferencesResponseDTO(
    val id: Long,
    val theme: String,
    val colorScheme: String,
    val categories: String,
    val labels: String,
    val userId: Long?
)