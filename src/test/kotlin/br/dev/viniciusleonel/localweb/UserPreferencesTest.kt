package br.dev.viniciusleonel.localweb

import br.dev.viniciusleonel.localweb.model.UserPreferences
import br.dev.viniciusleonel.localweb.repository.UserPreferencesRepository
import br.dev.viniciusleonel.localweb.service.UserPreferencesService
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest
class UserPreferencesServiceTest {

    @MockBean
    private lateinit var repository: UserPreferencesRepository

    @Autowired
    private lateinit var service: UserPreferencesService

//    @Autowired
//    private val mockMvc: MockMvc? = null

    @Test
    fun `should return user preferences`() {
        val userId = 1L
        val preferences = UserPreferences(id = 1L, userId = userId, theme = "dark", colorScheme = "blue", categories = "tech")

        `when`(repository.findByUserId(userId)).thenReturn(Optional.of(preferences))

        val result = service.getUserPreferences(userId)

        assertEquals("dark", result.theme)
    }

//    @Test
//    fun `should return 404 if preferences not found`() {
//        val userId = 1L
//
//        given(service.getUserPreferences(userId)).willThrow(EntityNotFoundException::class.java)
//
//        mockMvc.perform(get("/api/preferences/{userId}", userId))
//            .andExpect(status().isNotFound)
//    }
}
