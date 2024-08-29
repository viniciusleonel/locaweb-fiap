package br.dev.viniciusleonel.localweb.dto.preferences

import jakarta.validation.constraints.NotNull

data class UserPreferencesDTO(

    val theme: String,
    val colorScheme: String,
    val categories: String,
    val labels: String,

    @field:NotNull(message = "User ID is required")
    val userId: Long
)
