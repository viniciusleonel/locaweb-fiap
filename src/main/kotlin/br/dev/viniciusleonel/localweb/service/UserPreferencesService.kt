package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.UserPreferencesDTO
import br.dev.viniciusleonel.localweb.dto.UserUpdatePreferencesDTO
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.repository.UserPreferencesRepository
import br.dev.viniciusleonel.localweb.repository.UserRepository
import br.dev.viniciusleonel.localweb.utils.toUserPreferences
import br.dev.viniciusleonel.localweb.utils.updateFromDTO
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPreferencesService(private val userPreferencesRepository: UserPreferencesRepository, private val userRepository: UserRepository) {

    @Transactional
    fun savePreferences(userPreferencesDTO: UserPreferencesDTO): UserPreferences {
        val user = userRepository.findById(userPreferencesDTO.userId)
            .orElseThrow { EntityNotFoundException("User not found with id: ${userPreferencesDTO.userId}") }

        val userPreferences = userPreferencesDTO.toUserPreferences(user)

        return userPreferencesRepository.save(userPreferences)
    }

    @Transactional
    fun updatePreferences(id: Long, updateDTO: UserUpdatePreferencesDTO): UserPreferences {
        val preferences = userPreferencesRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Preferences not found with id: $id") }

        preferences.updateFromDTO(updateDTO)
        return userPreferencesRepository.save(preferences)
    }

    @Transactional
    fun deletePreferences(id: Long) {
        val preferences = userPreferencesRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Preferences not found with id: $id") }
        userPreferencesRepository.delete(preferences)
    }
}