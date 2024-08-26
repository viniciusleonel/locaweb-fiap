package br.dev.viniciusleonel.localweb.repository

import br.dev.viniciusleonel.localweb.model.UserPreferences
import org.springframework.data.jpa.repository.JpaRepository

interface UserPreferencesRepository: JpaRepository<UserPreferences, Long> {
}