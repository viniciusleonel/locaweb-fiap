package br.dev.viniciusleonel.localweb.infra.exception


class CustomException(
    val errorMessage: String,
    val details: String? = null
) : RuntimeException(errorMessage)
