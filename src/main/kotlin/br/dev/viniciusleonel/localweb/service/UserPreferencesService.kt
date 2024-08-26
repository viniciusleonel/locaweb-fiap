package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.dto.UserPreferencesDTO
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.repository.UserPreferencesRepository
import br.dev.viniciusleonel.localweb.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserPreferencesService(private val userPreferencesRepository: UserPreferencesRepository, private val userRepository: UserRepository) {

    @Transactional
    fun savePreferences(userPreferencesDTO: UserPreferencesDTO): UserPreferences {
        val user = userRepository.findById(userPreferencesDTO.userId)
            .orElseThrow { EntityNotFoundException("User not found with id: ${userPreferencesDTO.userId}") }

        val userPreferences = UserPreferences(
            theme = userPreferencesDTO.theme,
            colorScheme = userPreferencesDTO.colorScheme,
            categories = userPreferencesDTO.categories,
            labels = userPreferencesDTO.labels,
            user = user
        )

        return userPreferencesRepository.save(userPreferences)
    }
}