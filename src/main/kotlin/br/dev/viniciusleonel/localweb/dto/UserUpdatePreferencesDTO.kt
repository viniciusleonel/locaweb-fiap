package br.dev.viniciusleonel.localweb.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserUpdatePreferencesDTO(
    val theme: String? = null,
    @JsonProperty("color_scheme")
    val colorScheme: String? = null,
    val categories: String? = null,
    val labels: String? = null
)
