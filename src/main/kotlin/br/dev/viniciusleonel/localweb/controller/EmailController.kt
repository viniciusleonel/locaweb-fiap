package br.dev.viniciusleonel.localweb.controller

import br.dev.viniciusleonel.localweb.model.Email
import br.dev.viniciusleonel.localweb.service.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/emails")
class EmailController(private val emailService: EmailService) {

    @GetMapping
    fun listEmails(): ResponseEntity<List<Email>> {
        return ResponseEntity.ok(emailService.listEmails())
    }

    @PostMapping
    fun sendEmail(@RequestBody email: Email): ResponseEntity<Email> {
        val sentEmail = emailService.sendEmail(email)
        return ResponseEntity.ok(sentEmail)
    }

    @GetMapping("/{id}")
    fun getEmailDetails(@PathVariable id: Long): ResponseEntity<Email> {
        val email = emailService.getEmailDetails(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(email)
    }
}
