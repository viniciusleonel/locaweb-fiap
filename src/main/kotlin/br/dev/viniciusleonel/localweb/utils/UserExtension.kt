package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.UserDTO
import br.dev.viniciusleonel.localweb.dto.UserUpdateDTO
import br.dev.viniciusleonel.localweb.infra.security.EncodeService
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException

fun UserDTO.exists(repository: UserRepository, userDTO: UserDTO) : UserDTO{
    if (repository.existsByUsername(userDTO.username.lowercase().replace(" ", ""))) {
        throw DataIntegrityViolationException("Username already exists: ${userDTO.username.replace(" ", "")}")
    }

    if (repository.existsByEmail(userDTO.email.lowercase())) {
        throw DataIntegrityViolationException("Email already exists: ${userDTO.email.lowercase()}")
    }

    return this
}

// Função de extensão para converter um UserDTO em um User
fun UserDTO.toModel(passwordService: EncodeService): User {
    return User(
        name = this.name.capitalizeFirstLetter(),
        email = this.email.lowercase(),
        username = this.username.lowercase().replace(" ", ""),
        password = passwordService.encodePassword(this.password)
    )
}

// Função de extensão para atualizar um User com base em UserUpdateDTO
fun User.updateFromDTO(updateUserDTO: UserUpdateDTO, passwordService: EncodeService) {
    updateUserDTO.name?.takeIf { it.isNotBlank() }?.let { this.name = it.capitalizeFirstLetter() }
    updateUserDTO.username?.takeIf { it.isNotBlank() }?.let { this.username = it.lowercase().replace(" ", "") }
    updateUserDTO.email?.takeIf { it.isNotBlank() }?.let { this.email = it.lowercase() }
    updateUserDTO.password?.takeIf { it.isNotBlank() }?.let { this.password = passwordService.encodePassword(it) }
}