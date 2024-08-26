package br.dev.viniciusleonel.localweb.controller

import br.dev.viniciusleonel.localweb.dto.UserPreferencesDTO
import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.service.UserPreferencesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.prefs.Preferences

@RestController
@RequestMapping("/api/preferences")
class UserPreferencesController(private val service: UserPreferencesService) {

    @PostMapping
    fun savePreferences(@RequestBody preferencesDTO: UserPreferencesDTO): ResponseEntity<UserPreferences> {
        val savedPreferences = service.savePreferences(preferencesDTO)
        return ResponseEntity.ok(savedPreferences)
    }

}