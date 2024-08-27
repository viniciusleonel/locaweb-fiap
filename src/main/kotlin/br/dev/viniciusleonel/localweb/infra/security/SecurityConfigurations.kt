package br.dev.viniciusleonel.localweb.infra.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig  {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { csrf -> csrf.disable() }
            .sessionManagement { sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { req -> req.anyRequest().permitAll() }  // Permite todas as requisições
            .build()
    }

}