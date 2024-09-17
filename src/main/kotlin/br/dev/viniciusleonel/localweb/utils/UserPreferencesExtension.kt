package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.preferences.UserPreferencesDTO
import br.dev.viniciusleonel.localweb.dto.preferences.UserUpdatePreferencesDTO
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.model.UserPreferences

fun UserPreferencesDTO.toUserPreferences(user: User): UserPreferences {
    return UserPreferences(
        theme = this.theme,
        colorScheme = this.colorScheme,
        categories = this.categories,
        labels = this.labels,
        user = user
    )
}

fun UserPreferences.updateFromDTO(updateDTO: UserUpdatePreferencesDTO) {
    updateDTO.theme?.takeIf { it.isNotBlank() }?.let { this.theme = it }
    updateDTO.colorScheme?.takeIf { it.isNotBlank() }?.let { this.colorScheme = it }
    updateDTO.categories?.takeIf { it.isNotBlank() }?.let { this.categories = it }
    updateDTO.labels?.takeIf { it.isNotBlank() }?.let { this.labels = it }
}

