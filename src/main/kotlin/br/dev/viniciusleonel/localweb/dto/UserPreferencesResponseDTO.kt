package br.dev.viniciusleonel.localweb.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserPreferencesResponseDTO(
    val id: Long,
    val theme: String,
    @JsonProperty("color_scheme")
    val colorScheme: String,
    val categories: String,
    val labels: String,
    val userId: Long?
)