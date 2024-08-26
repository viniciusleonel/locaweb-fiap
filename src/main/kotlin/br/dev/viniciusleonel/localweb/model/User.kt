package br.dev.viniciusleonel.localweb.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var email: String = "",
    var username: String = "",
    var password: String = "",

) {
    @OneToMany(mappedBy = "user")
    @JsonProperty("user_preferences")
    var userPreferences: MutableList<UserPreferences> = mutableListOf()

    @OneToMany(mappedBy = "user")
    var receivedEmails: MutableList<Email> = mutableListOf()

    @OneToMany(mappedBy = "user")
    var sentEmails: MutableList<Email> = mutableListOf()
}
