package br.dev.viniciusleonel.localweb.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "user_preferences")
data class UserPreferences(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var theme: String = "",

    var colorScheme:String = "",

    var categories: String = "",

    var labels: String = "",

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    var user: User? = null
)