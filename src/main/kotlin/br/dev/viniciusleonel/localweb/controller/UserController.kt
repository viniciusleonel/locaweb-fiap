package br.dev.viniciusleonel.localweb.controller

import br.dev.viniciusleonel.localweb.dto.*
import br.dev.viniciusleonel.localweb.dto.preferences.UserPreferencesDTO
import br.dev.viniciusleonel.localweb.dto.user.*
import br.dev.viniciusleonel.localweb.model.User
import br.dev.viniciusleonel.localweb.service.UserPreferencesService
import br.dev.viniciusleonel.localweb.service.UserService
import br.dev.viniciusleonel.localweb.utils.toUserPreferences
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val service: UserService,
    private val userPreferencesService: UserPreferencesService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginDTO: UserLogInDTO): ResponseEntity<UserDetailsDTO> {
        val user = service.login(loginDTO)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/logout")
    fun logout(@Valid @RequestBody logoutDTO : UserLogOutDTO): ResponseEntity<MessageDTO> {
        val response = service.logout(logoutDTO)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun insertUser(@RequestBody @Valid userDTO: UserDTO): ResponseEntity<User> {
        val user = service.insertUser(userDTO)
        val userPreferences = UserPreferencesDTO("", "", "", "", user.id)
        user.userPreferences = userPreferences.toUserPreferences(user)
        service.saveUserWithPreferences(user)
        return ResponseEntity.ok(user)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody @Valid userDTO: UserUpdateDTO): ResponseEntity<User> {
        val updateUser = service.updateUser(userId, userDTO)
        return ResponseEntity.ok(updateUser)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<UserListDTO> {
        val preferences = service.getUserById(userId)
        return ResponseEntity.ok(preferences)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<MessageDTO> {
        val response = service.deleteUser(userId)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun listUsers(@PageableDefault(size = 10, sort = ["id"])pageable: Pageable ): ResponseEntity<Page<UserListDTO>> {
        val page = service.listUsers(pageable)
        return ResponseEntity.ok(page)
    }
}
