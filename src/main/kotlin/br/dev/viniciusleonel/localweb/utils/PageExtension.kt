package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.model.UserPreferences
import org.springframework.data.domain.Page

fun Page<User>.toUserPreferencesModel(): Page<User> {
    return this.map { it }
}


