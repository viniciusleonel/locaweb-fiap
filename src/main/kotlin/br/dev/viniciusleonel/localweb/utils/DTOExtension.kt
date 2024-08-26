package br.dev.viniciusleonel.localweb.utils

import br.dev.viniciusleonel.localweb.dto.UserDTO
import br.dev.viniciusleonel.localweb.dto.UserUpdateDTO
import br.dev.viniciusleonel.localweb.model.User

fun UserDTO.toModel(): User {
    return User(
        email = this.email,
        username = this.username,
        password = this.password
    )
}

fun UserUpdateDTO.toModel(): User {
    return User(
        email = this.email.toString(),
        username = this.username.toString(),
        password = this.password.toString()
    )
}
