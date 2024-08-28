package br.dev.viniciusleonel.localweb.utils

fun String.capitalizeFirstLetter(): String {
    return this.split(" ")
        .joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercaseChar() } }
}

