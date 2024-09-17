package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.MessageDTO
import br.dev.viniciusleonel.localweb.dto.preferences.UserUpdatePreferencesDTO
import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.repository.UserPreferencesRepository
import br.dev.viniciusleonel.localweb.repository.UserRepository
import br.dev.viniciusleonel.localweb.utils.isActive
import br.dev.viniciusleonel.localweb.utils.updateFromDTO
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPreferencesService(private val userPreferencesRepository: UserPreferencesRepository, private val userRepository: UserRepository) {

    @Transactional
    fun updatePreferences(id: Long, updateDTO: UserUpdatePreferencesDTO): UserPreferences {
        val preferences = userPreferencesRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Preferences not found with id: '$id'") }
        val user = preferences.user?.let {
            userRepository.findById(it.id)
                .orElseThrow { EntityNotFoundException("User not found with id: '${preferences.user!!.id}'") }
        }
        user?.isActive(userRepository)
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
        user?.isActive(userRepository)
        userPreferencesRepository.delete(preferences)
        return MessageDTO("Preference '${preferences.id}' is deleted")
    }

    fun getPreferencesByUserId(userId: Long): UserPreferences {
        val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found with id: '$userId'") }
        user.isActive(userRepository)
        return userPreferencesRepository.findAllByUserId(userId)
    }

}