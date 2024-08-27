package br.dev.viniciusleonel.localweb.infra.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class EncodeService {

    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    fun encodePassword(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}
