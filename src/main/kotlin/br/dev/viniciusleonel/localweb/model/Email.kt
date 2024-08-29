package br.dev.viniciusleonel.localweb.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "emails")
data class Email(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "sent_by_user_id")
    @JsonBackReference
    val sentByUser: User,

    @ManyToOne
    @JoinColumn(name = "received_by_user_id")
    @JsonBackReference
    val receivedByUser: User? = null,

    val subject: String,
    val body: String,

) {
    val sentAt: LocalDateTime = LocalDateTime.now()
    var wasRead: Boolean = false
}

