package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.UserDTO
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
        var user = userDTO.toModel()
        repository.save(user)
    }

    fun findAll(): MutableList<User> {
        return repository.findAll()
    }

    fun finAllPageable(pageable: Pageable): Page<User> {
        val page = repository.findAll(pageable)
        return page.toUserPreferencesModel()
    }

//    fun updateUserPreferences(userId: Long, preferences: UserPreferences): UserPreferences {
//        val existingPreferences = getUserPreferences(userId)
//        existingPreferences.theme = preferences.theme
//        existingPreferences.colorScheme = preferences.colorScheme
//        existingPreferences.categories = preferences.categories
//        return repository.save(existingPreferences)
//    }



}
