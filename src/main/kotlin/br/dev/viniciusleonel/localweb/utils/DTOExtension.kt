package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.UserDTO
import br.dev.viniciusleonel.localweb.dto.UserPreferencesDTO
import br.dev.viniciusleonel.localweb.dto.UserUpdateDTO
import br.dev.viniciusleonel.localweb.dto.UserUpdatePreferencesDTO
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.model.UserPreferences

// Função de extensão para converter um UserDTO em um User
fun UserDTO.toModel(): User {
    return User(
        email = this.email,
        username = this.username,
        password = this.password
    )
}

// Função de extensão para atualizar um User com base em UserUpdateDTO
fun User.updateFromDTO(updateUserDTO: UserUpdateDTO) {
    updateUserDTO.username?.takeIf { it.isNotBlank() }?.let { this.username = it }
    updateUserDTO.email?.takeIf { it.isNotBlank() }?.let { this.email = it }
    updateUserDTO.password?.takeIf { it.isNotBlank() }?.let { this.password = it }
}

// Função de extensão para criar um UserPreferences a partir do DTO
fun UserPreferencesDTO.toUserPreferences(user: User): UserPreferences {
    return UserPreferences(
        theme = this.theme ?: "",
        colorScheme = this.colorScheme ?: "",
        categories = this.categories ?: "",
        labels = this.labels ?: "",
        user = user
    )
}

// Função de extensão para atualizar um UserPreferences com base em UserPreferencesDTO
fun UserPreferences.updateFromDTO(updateDTO: UserUpdatePreferencesDTO) {
    updateDTO.theme?.takeIf { it.isNotBlank() }?.let { this.theme = it }
    updateDTO.colorScheme?.takeIf { it.isNotBlank() }?.let { this.colorScheme = it }
    updateDTO.categories?.takeIf { it.isNotBlank() }?.let { this.categories = it }
    updateDTO.labels?.takeIf { it.isNotBlank() }?.let { this.labels = it }
}
