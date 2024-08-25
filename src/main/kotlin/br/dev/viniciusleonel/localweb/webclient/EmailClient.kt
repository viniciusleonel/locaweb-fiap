package br.dev.viniciusleonel.localweb.webclient

import br.dev.viniciusleonel.localweb.model.Email
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class EmailClient(private val restTemplate: RestTemplate) {

    fun listEmails(): List<Email> {
        val url = "http://localhost:8080/api/emails"
        return restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<Email>>() {}
        ).body!!
    }

    fun sendEmail(email: Email): Email? {
        val url = "http://localhost:8080/api/emails"
        return restTemplate.postForObject(url, email, Email::class.java)
    }

    fun getEmailDetails(id: Long): Email? {
        val url = "http://localhost:8080/api/emails/$id"
        return restTemplate.getForObject(url, Email::class.java)
    }
}
