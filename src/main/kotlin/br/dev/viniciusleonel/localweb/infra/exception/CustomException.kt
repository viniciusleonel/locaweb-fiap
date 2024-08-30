package br.dev.viniciusleonel.localweb.infra.exception

import org.springframework.http.HttpStatus


class CustomException(
    message: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST
) : RuntimeException(message)

