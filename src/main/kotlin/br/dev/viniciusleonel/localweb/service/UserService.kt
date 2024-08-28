package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.*
import br.dev.viniciusleonel.localweb.infra.exception.CustomException
import br.dev.viniciusleonel.localweb.infra.security.EncodeService
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.repository.UserRepository
import br.dev.viniciusleonel.localweb.utils.exists
import br.dev.viniciusleonel.localweb.utils.toModel
import br.dev.viniciusleonel.localweb.utils.toUserPreferencesModel
import br.dev.viniciusleonel.localweb.utils.updateFromDTO
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repository: UserRepository, private val passwordService: EncodeService) {
    fun getUserById(id: Long): User {
        return repository.findById(id).orElseThrow { EntityNotFoundException("User not found with id: $id") }
    }

    @Transactional
    fun login(loginDTO: UserLogInDTO): User {
        val user = repository.findByUsername(loginDTO.username)
            ?: throw EntityNotFoundException("User not found with username: ${loginDTO.username}")
        if (!passwordService.matches(loginDTO.password, user.password)) {
            throw CustomException("Invalid password")
        }
        user.logIn()
        return user
    }

    @Transactional
    fun logout(logoutDTO: UserLogOutDTO): MessageDTO {
        val user = repository.findByUsername(logoutDTO.username)
            ?: throw EntityNotFoundException("User not found with username: ${logoutDTO.username}")
        if (user.isLoggedIn) {
            user.logOut()
            return MessageDTO("User ${logoutDTO.username} is logged out!")
        } else {
            throw CustomException("User ${logoutDTO.username} is not logged in")
        }
    }

    @Transactional
    fun insertUser(userDTO: UserDTO): User {
        userDTO.exists(repository, userDTO)
        val user = userDTO.toModel(passwordService)
        return repository.save(user)
    }

    fun listUsers(pageable: Pageable): Page<User> {
        val page = repository.findAll(pageable)
        return page.toUserPreferencesModel()
    }

    @Transactional
    fun updateUser(id: Long, updateUserDTO: UserUpdateDTO): User {
        // Encontre o usuário existente
        val user = repository.findById(id)
            .orElseThrow { EntityNotFoundException("User not found with id: $id") }

        // Atualize os campos conforme fornecido no DTO, com validação
        user.updateFromDTO(updateUserDTO, passwordService)

        // Salve as alterações no mesmo objeto
        return repository.save(user)
    }


    @Transactional
    fun deleteUser(id: Long) {
        val user = repository.findById(id)
        .orElseThrow { EntityNotFoundException("User not found with id: $id") }
        repository.delete(user)
    }

}
