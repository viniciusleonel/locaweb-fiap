package br.dev.viniciusleonel.localweb.controller

import br.dev.viniciusleonel.localweb.dto.UserDTO
import br.dev.viniciusleonel.localweb.dto.UserUpdateDTO
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(private val service: UserService) {

    @PostMapping
    fun insertUser(@RequestBody @Valid userDTO: UserDTO): ResponseEntity<UserDTO> {
        service.insertUserPreferences(userDTO)
        return ResponseEntity.ok(userDTO)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody @Valid userDTO: UserUpdateDTO): ResponseEntity<User> {
        val updateUser = service.updateUser(userId, userDTO)
        return ResponseEntity.ok(updateUser)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<User> {
        val preferences = service.getUserPreferences(userId)
        return ResponseEntity.ok(preferences)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<Void> {
        service.deleteUser(userId)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getAll(@PageableDefault(size = 10, sort = ["id"])pageable: Pageable ): ResponseEntity<Page<User>> {
        val list = service.finAllPageable(pageable)
        return ResponseEntity.ok(list)
    }


}
