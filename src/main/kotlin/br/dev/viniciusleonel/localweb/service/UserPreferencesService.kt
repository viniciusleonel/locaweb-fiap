package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.repository.UserPreferencesRepository
import br.dev.viniciusleonel.localweb.utils.toUserPreferencesModel
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPreferencesService(private val repository: UserPreferencesRepository) {
    fun getUserPreferences(userId: Long): UserPreferences {
        return repository.findById(userId).orElseThrow { EntityNotFoundException("Preferences not found") }
    }

    fun findAll(): MutableList<UserPreferences> {
        return repository.findAll()
    }

    fun finAllPageable(pageable: Pageable): Page<UserPreferences> {
        val page = repository.findAll(pageable)
        return page.toUserPreferencesModel()
    }

    fun updateUserPreferences(userId: Long, preferences: UserPreferences): UserPreferences {
        val existingPreferences = getUserPreferences(userId)
        existingPreferences.theme = preferences.theme
        existingPreferences.colorScheme = preferences.colorScheme
        existingPreferences.categories = preferences.categories
        return repository.save(existingPreferences)
    }

    @Transactional
    fun insertUserPreferences(userPreferences: UserPreferences) {
        repository.save(userPreferences)
    }

}
