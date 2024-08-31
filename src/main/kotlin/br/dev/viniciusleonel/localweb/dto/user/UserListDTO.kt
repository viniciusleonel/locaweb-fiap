package br.dev.viniciusleonel.localweb.dto.user

import br.dev.viniciusleonel.localweb.dto.email.ReceivedEmailDTO
import br.dev.viniciusleonel.localweb.dto.email.SentEmailDTO
import br.dev.viniciusleonel.localweb.model.UserPreferences

class UserListDTO(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val isLoggedIn: Boolean,
    val status: Boolean,
    val userPreferences: MutableList<UserPreferences>,
    val sentEmails: MutableList<SentEmailDTO>,
    val receivedEmails: MutableList<ReceivedEmailDTO>
)