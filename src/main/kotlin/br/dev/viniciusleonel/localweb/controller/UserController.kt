package br.dev.viniciusleonel.localweb.controller

import br.dev.viniciusleonel.localweb.dto.UserDTO
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/preferences")
class UserController(private val service: UserService) {

    @PostMapping
    fun insertPreferences(@RequestBody @Valid userDTO: UserDTO): ResponseEntity<UserDTO> {
        service.insertUserPreferences(userDTO)
        return ResponseEntity.ok(userDTO)
    }

    @GetMapping("/{userId}")
    fun getPreferences(@PathVariable userId: Long): ResponseEntity<User> {
        val preferences = service.getUserPreferences(userId)
        return ResponseEntity.ok(preferences)
    }

//    @GetMapping
//    fun getAll(): ResponseEntity<MutableList<UserPreferences>> {
//        val list = service.findAll()
//        return ResponseEntity.ok(list)
//    }

    @GetMapping
    fun getAll(@PageableDefault(size = 10, sort = ["id"])pageable: Pageable ): ResponseEntity<Page<User>> {
        val list = service.finAllPageable(pageable)
        return ResponseEntity.ok(list)
    }

//    @PutMapping("/{userId}")
//    fun updatePreferences(@PathVariable userId: Long, @RequestBody preferences: UserPreferences): ResponseEntity<UserPreferences> {
//        val updatedPreferences = service.updateUserPreferences(userId, preferences)
//        return ResponseEntity.ok(updatedPreferences)
//    }
}
