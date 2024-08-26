package br.dev.viniciusleonel.localweb.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserPreferencesDTO(
    val theme: String,
    @JsonProperty("color_scheme") val colorScheme: String,
    val categories: String,
    val labels: String,
    @JsonProperty("user_id") val userId: Long
)
