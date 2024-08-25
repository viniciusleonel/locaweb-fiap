package br.dev.viniciusleonel.localweb.repository

import br.dev.viniciusleonel.localweb.model.UserPreferences
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable
import java.util.*

@Repository
interface UserPreferencesRepository: JpaRepository<UserPreferences, Long> {

    override fun findAll(pageable: Pageable ) : Page<UserPreferences>

}