package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.model.UserPreferences
import org.springframework.data.domain.Page

fun Page<UserPreferences>.toUserPreferencesModel(): Page<UserPreferences> {
    // Aqui você pode aplicar qualquer lógica adicional se necessário
    return this.map { it } // No caso, apenas retorna o mesmo item
}
