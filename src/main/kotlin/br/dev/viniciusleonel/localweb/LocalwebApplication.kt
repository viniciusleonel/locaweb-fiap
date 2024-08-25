package br.dev.viniciusleonel.localweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LocalwebApplication

fun main(args: Array<String>) {
    runApplication<LocalwebApplication>(*args)
}
