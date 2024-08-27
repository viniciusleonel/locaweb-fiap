package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.UserDTO
import br.dev.viniciusleonel.localweb.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException

fun UserDTO.exists(repository: UserRepository, userDTO: UserDTO) : UserDTO{
    if (repository.existsByUsername(userDTO.username)) {
        throw DataIntegrityViolationException("Username already exists: ${userDTO.username}")
    }

    if (repository.existsByEmail(userDTO.email)) {
        throw DataIntegrityViolationException("Email already exists: ${userDTO.email}")
    }

    return this
}