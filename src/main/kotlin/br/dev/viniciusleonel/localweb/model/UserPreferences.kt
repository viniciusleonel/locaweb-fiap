package br.dev.viniciusleonel.localweb.model

import jakarta.persistence.*

@Entity
@Table(name = "user_preferences")
data class UserPreferences(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val userId: Long,
    var theme: String,
    var colorScheme:String,
    var categories: String
)