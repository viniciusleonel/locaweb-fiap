package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.MessageDTO
import br.dev.viniciusleonel.localweb.dto.preferences.UserPreferencesDTO
import br.dev.viniciusleonel.localweb.dto.preferences.UserUpdatePreferencesDTO
import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.repository.UserPreferencesRepository
import br.dev.viniciusleonel.localweb.repository.UserRepository
import br.dev.viniciusleonel.localweb.utils.isActive
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
            .orElseThrow { EntityNotFoundException("User not found with id: '${userPreferencesDTO.userId}'") }
        user.isActive(userRepository, user)
        val userPreferences = userPreferencesDTO.toUserPreferences(user)
        return userPreferencesRepository.save(userPreferences)
    }

    @Transactional
    fun updatePreferences(id: Long, updateDTO: UserUpdatePreferencesDTO): UserPreferences {
        val preferences = userPreferencesRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Preferences not found with id: '$id'") }
        val user = preferences.user?.let {
            userRepository.findById(it.id)
                .orElseThrow { EntityNotFoundException("User not found with id: '${preferences.user!!.id}'") }
        }
        user?.isActive(userRepository, user)
        preferences.updateFromDTO(updateDTO)
        return userPreferencesRepository.save(preferences)
    }

    @Transactional
    fun deletePreferences(id: Long) : MessageDTO {
        val preferences = userPreferencesRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Preferences not found with id: '$id'") }
        val user = preferences.user?.let {
            userRepository.findById(it.id)
                .orElseThrow { EntityNotFoundException("User not found with id: '${preferences.user!!.id}'") }
        }
        user?.isActive(userRepository, user)
        userPreferencesRepository.delete(preferences)
        return MessageDTO("Preference '${preferences.id}' is deleted")
    }

    fun getPreferencesById(id: Long): UserPreferences {
        val preferences = userPreferencesRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Preferences not found with id: '$id'") }
        val user = preferences.user?.let {
            userRepository.findById(it.id)
                .orElseThrow { EntityNotFoundException("User not found with id: '${preferences.user!!.id}'") }
        }
        user?.isActive(userRepository, user)
        return preferences
    }

    fun getPreferencesByUserId(userId: Long): UserPreferences {
        val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found with id: '$userId'") }
        user.isActive(userRepository, user)
        return userPreferencesRepository.findAllByUserId(userId)
    }

}