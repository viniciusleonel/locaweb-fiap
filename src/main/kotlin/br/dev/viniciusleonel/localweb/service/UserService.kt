package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.UserDTO
import br.dev.viniciusleonel.localweb.dto.UserUpdateDTO
import br.dev.viniciusleonel.localweb.infra.exception.NotFoundException
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.repository.UserRepository
import br.dev.viniciusleonel.localweb.utils.toModel
import br.dev.viniciusleonel.localweb.utils.toUserPreferencesModel
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repository: UserRepository) {
    fun getUserPreferences(id: Long): User {
        return repository.findById(id).orElseThrow { EntityNotFoundException("Preferences not found") }
    }

    @Transactional
    fun insertUserPreferences(userDTO: UserDTO) {
        val user = userDTO.toModel()
        repository.save(user)
    }

    fun finAllPageable(pageable: Pageable): Page<User> {
        val page = repository.findAll(pageable)
        return page.toUserPreferencesModel()
    }

    @Transactional
    fun updateUser(id: Long, updateUserDTO: UserUpdateDTO): User {
        // Encontre o usuário existente
        val user = repository.findById(id)
            .orElseThrow { NotFoundException("User not found with id: $id") }

        // Atualize os campos conforme fornecido no DTO
        updateUserDTO.username?.let { user.username = it }
        updateUserDTO.email?.let { user.email = it }
        updateUserDTO.password?.let { user.password = it }

        // Salve as alterações no mesmo objeto
        return repository.save(user)
    }

    @Transactional
    fun deleteUser(id: Long) {
        val user = repository.findById(id)
        .orElseThrow { NotFoundException("User not found with id: $id") }
        repository.delete(user)
    }

}
