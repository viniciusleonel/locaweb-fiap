package br.dev.viniciusleonel.localweb

import br.dev.viniciusleonel.localweb.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class LocalwebApplication(val userRepository: UserRepository) {

    @Bean
    fun setAllUsersLoggedOut() = CommandLineRunner {
        userRepository.updateAllUsersLoggedOut()
    }
}

fun main(args: Array<String>) {
    runApplication<LocalwebApplication>(*args)


}
