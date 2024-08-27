package br.dev.viniciusleonel.localweb.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class UserPreferencesDTO(
    val theme: String,
    @JsonProperty("color_scheme") val colorScheme: String,
    val categories: String,
    val labels: String,

    @field:NotNull(message = "User ID is mandatory")
    @JsonProperty("user_id")
    val userId: Long
)
