package br.dev.viniciusleonel.localweb.controller

import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.service.UserPreferencesService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/preferences")
class UserPreferencesController(private val service: UserPreferencesService) {

    @PostMapping
    fun insertPreferences(@RequestBody userPreferences: UserPreferences): ResponseEntity<UserPreferences> {
        service.insertUserPreferences(userPreferences)
        return ResponseEntity.ok(userPreferences)
    }

    @GetMapping("/{userId}")
    fun getPreferences(@PathVariable userId: Long): ResponseEntity<UserPreferences> {
        val preferences = service.getUserPreferences(userId)
        return ResponseEntity.ok(preferences)
    }

//    @GetMapping
//    fun getAll(): ResponseEntity<MutableList<UserPreferences>> {
//        val list = service.findAll()
//        return ResponseEntity.ok(list)
//    }

    @GetMapping
    fun getAll(@PageableDefault(size = 10, sort = ["id"])pageable: Pageable ): ResponseEntity<Page<UserPreferences>> {
        val list = service.finAllPageable(pageable)
        return ResponseEntity.ok(list)
    }

//    @PutMapping("/{userId}")
//    fun updatePreferences(@PathVariable userId: Long, @RequestBody preferences: UserPreferences): ResponseEntity<UserPreferences> {
//        val updatedPreferences = service.updateUserPreferences(userId, preferences)
//        return ResponseEntity.ok(updatedPreferences)
//    }
}
