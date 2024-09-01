package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.user.UserDTO
import br.dev.viniciusleonel.localweb.dto.user.UserDetailsDTO
import br.dev.viniciusleonel.localweb.dto.user.UserListDTO
import br.dev.viniciusleonel.localweb.dto.user.UserUpdateDTO
import br.dev.viniciusleonel.localweb.infra.exception.CustomException
import br.dev.viniciusleonel.localweb.infra.security.EncodeService
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus

fun UserDTO.exists(repository: UserRepository, userDTO: UserDTO) : UserDTO {
    if (repository.existsByUsername(userDTO.username.lowercase().replace(" ", ""))) {
        throw CustomException("Username already exists: ${userDTO.username.replace(" ", "")}")
    }

    if (repository.existsByEmail(userDTO.email.lowercase())) {
        throw CustomException("Email already exists: ${userDTO.email.lowercase()}")
    }

    return this
}

fun User.isActive(repository: UserRepository, user: User, endpoint: String = "")  {
    val user1 = repository.findByUsername(user.username)
    if (user1 != null) {
        if (!user1.status) {
            throw CustomException("User not found with username: ${user1.username}",  HttpStatus.NOT_FOUND)
        }
        if (!user1.isLoggedIn && !endpoint.equals("login", ignoreCase = true)) {
            throw CustomException("User '${user1.username}' is not logged in.", HttpStatus.UNAUTHORIZED)
        }
    }
    return
}

fun UserDTO.toModel(passwordService: EncodeService): User {
    return User(
        name = this.name.capitalizeFirstLetter(),
        email = this.email.lowercase(),
        username = this.username.lowercase().replace(" ", ""),
        password = passwordService.encodePassword(this.password)
    )
}

fun User.updateFromDTO(updateUserDTO: UserUpdateDTO, passwordService: EncodeService) {
    updateUserDTO.name?.takeIf { it.isNotBlank() }?.let { this.name = it.capitalizeFirstLetter() }
    updateUserDTO.username?.takeIf { it.isNotBlank() }?.let { this.username = it.lowercase().replace(" ", "") }
    updateUserDTO.email?.takeIf { it.isNotBlank() }?.let { this.email = it.lowercase() }
    updateUserDTO.password?.takeIf { it.isNotBlank() }?.let { this.password = passwordService.encodePassword(it) }
}

fun User.toUserDetailsDTO(user: User): UserDetailsDTO {
    return UserDetailsDTO(
        id = this.id,
        name = this.name,
        username = this.username,
        email = this.email,
        isLoggedIn = this.isLoggedIn,
    )
}

fun User.toUserListDTO(): UserListDTO {
    return UserListDTO(
        id = this.id,
        name = this.name,
        username = this.username,
        email = this.email,
        isLoggedIn = this.isLoggedIn,
        status = this.status,
        userPreferences = this.userPreferences,
        sentEmails = this.getSentEmailsDTO(),
        receivedEmails = this.getReceivedEmailsDTO()
    )
}

fun Page<User>.toUserListDTOPage(): Page<UserListDTO> {
    return this.map { it.toUserListDTO() }
}