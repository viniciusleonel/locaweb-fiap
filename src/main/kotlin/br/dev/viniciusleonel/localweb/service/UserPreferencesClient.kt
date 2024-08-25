package br.dev.viniciusleonel.localweb.service

import br.dev.viniciusleonel.localweb.model.UserPreferences
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserPreferencesClient(private val restTemplate: RestTemplate) {

    fun getUserPreferences(userId: Long): UserPreferences? {
        val url = "http://localhost:8080/api/preferences/$userId"
        return restTemplate.getForObject(url, UserPreferences::class.java)
    }

    fun updateUserPreferences(userId: Long, preferences: UserPreferences): UserPreferences {
        val url = "http://localhost:8080/api/preferences/$userId"
        return restTemplate.exchange(
            url,
            HttpMethod.PUT,
            HttpEntity(preferences),
            UserPreferences::class.java
        ).body!!
    }
}
