package br.dev.viniciusleonel.localweb.infra.exception


class CustomException(
    private val errorMessage: String
) : RuntimeException(errorMessage)
