package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.*
import br.dev.viniciusleonel.localweb.dto.user.*
import br.dev.viniciusleonel.localweb.infra.exception.CustomException
import br.dev.viniciusleonel.localweb.infra.security.EncodeService
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.repository.UserRepository
import br.dev.viniciusleonel.localweb.utils.*
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repository: UserRepository, private val passwordService: EncodeService) {

    fun getUserById(id: Long): User {
        val user = repository.findById(id).orElseThrow { EntityNotFoundException("User not found with id: '$id'") }
        user.isActive(repository, user)
        return user
    }

    @Transactional
    fun login(loginDTO: UserLogInDTO): UserDetailsDTO {
        val user = repository.findByUsername(loginDTO.username)
            ?: throw EntityNotFoundException("User not found with username: '${loginDTO.username}'")
        user.isActive(repository, user, "login")
        if (!passwordService.matches(loginDTO.password, user.password)) {
            throw CustomException("Invalid password")
        }
        user.logIn()
        repository.save(user)
        return user.toUserDetailsDTO(user)
    }

    @Transactional
    fun logout(logoutDTO: UserLogOutDTO): MessageDTO {
        val user = repository.findByUsername(logoutDTO.username)
            ?: throw EntityNotFoundException("User not found with username: '${logoutDTO.username}'")
        user.isActive(repository, user)
        if (user.isLoggedIn) {
            user.logOut()
            return MessageDTO("User '${logoutDTO.username}' is logged out!")
        } else {
            throw CustomException("User '${logoutDTO.username}' is not logged in")
        }
    }

    @Transactional
    fun insertUser(userDTO: UserDTO): User {
        userDTO.exists(repository, userDTO)
        val user = userDTO.toModel(passwordService)
        return repository.save(user)
    }

    fun listUsers(pageable: Pageable): Page<User> {
        val page = repository.findAllByStatusTrue(pageable)
        return page
    }

    @Transactional
    fun updateUser(id: Long, updateUserDTO: UserUpdateDTO): User {
        val user = repository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found with id: '$id'") }
        user.isActive(repository, user)
        user.updateFromDTO(updateUserDTO, passwordService)

        return repository.save(user)
    }

    @Transactional
    fun deleteUser(id: Long): MessageDTO {
        val user = repository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found with id: '$id'") }
        user.isActive(repository, user)
        user.logOut()
        user.deleteUser()
        return MessageDTO("User '${user.username}' is deleted")
    }

}
