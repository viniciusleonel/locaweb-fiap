package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.UserDTO
import br.dev.viniciusleonel.localweb.model.User

fun UserDTO.toModel(): User {
    return User(
        email = this.email,
        username = this.username,
        password = this.password
    )
}
