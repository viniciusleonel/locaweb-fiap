package br.dev.viniciusleonel.localweb.controller

import br.dev.viniciusleonel.localweb.dto.MessageDTO
import br.dev.viniciusleonel.localweb.dto.preferences.UserPreferencesDTO
import br.dev.viniciusleonel.localweb.dto.preferences.UserPreferencesResponseDTO
import br.dev.viniciusleonel.localweb.dto.preferences.UserUpdatePreferencesDTO
import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.service.UserPreferencesService
import br.dev.viniciusleonel.localweb.utils.toResponseDTO
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/preferences")
class UserPreferencesController(private val service: UserPreferencesService) {

    @PutMapping("/{id}")
    fun updatePreferences(@PathVariable id: Long, @RequestBody @Valid updateDTO: UserUpdatePreferencesDTO): ResponseEntity<UserPreferences> {
        val updatedPreferences = service.updatePreferences(id, updateDTO)
        return ResponseEntity.ok(updatedPreferences)
    }

    @DeleteMapping("/{preferencesId}")
    fun deletePreferences(@PathVariable preferencesId: Long): ResponseEntity<MessageDTO> {
        val response = service.deletePreferences(preferencesId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/user/{userId}")
    fun getPreferencesByUserId(@PathVariable userId: Long): ResponseEntity<UserPreferences> {
        val preferences = service.getPreferencesByUserId(userId)
        return ResponseEntity.ok(preferences)
    }
}