package br.dev.viniciusleonel.localweb.controller

import br.dev.viniciusleonel.localweb.dto.MessageDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDetailsDTO
import br.dev.viniciusleonel.localweb.dto.email.EmailDetailsWithBodyDTO
import br.dev.viniciusleonel.localweb.dto.email.SentEmailDTO
import br.dev.viniciusleonel.localweb.service.EmailService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/email")
class EmailController(private val emailService: EmailService) {

    @PostMapping
    fun sendEmail(@RequestBody @Valid email: EmailDTO): ResponseEntity<SentEmailDTO> {
        val savedPreferences = emailService.sendEmail(email)
        return ResponseEntity.ok(savedPreferences)
    }

    @GetMapping("/user/{id}")
    fun listEmailsByUser(@PathVariable id: Long, @PageableDefault(size = 10, sort = ["id"])pageable: Pageable): ResponseEntity<Page<EmailDetailsDTO>> {
        val list = emailService.listEmailsByUser(id, pageable)
        return ResponseEntity.ok(list)
    }

    @GetMapping("/{emailId}")
    fun getEmailById(@PathVariable emailId: Long): ResponseEntity<EmailDetailsWithBodyDTO> {
        val preferences = emailService.getEmailById(emailId)
        return ResponseEntity.ok(preferences)
    }

    @DeleteMapping("/{emailId}")
    fun deleteEmailById(@PathVariable emailId: Long): ResponseEntity<MessageDTO> {
        val response = emailService.deleteEmailById(emailId)
        return ResponseEntity.ok(response)
    }

}
