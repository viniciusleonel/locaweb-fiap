package br.dev.viniciusleonel.localweb.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "emails")
data class Email(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val emailFrom: String,
    val emailTo: String,
    val subject: String,
    val body: String,
    val sentAt: LocalDateTime = LocalDateTime.now(),
    var wasRead: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    var user: User? = null
)

