package br.dev.viniciusleonel.localweb.model

import br.dev.viniciusleonel.localweb.dto.email.ReceivedEmailDTO
import br.dev.viniciusleonel.localweb.dto.email.SentEmailDTO
import br.dev.viniciusleonel.localweb.utils.toReceivedEmailDTO
import br.dev.viniciusleonel.localweb.utils.toSentEmailDTO
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var name: String = "",

    @Column(unique = true)
    var email: String = "",

    @Column(unique = true)
    var username: String = "",

    var password: String = "",
    var isLoggedIn: Boolean = false,
    var status: Boolean = true,

) {
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    var userPreferences: MutableList<UserPreferences> = mutableListOf()

    @OneToMany(mappedBy = "sentByUser")
    @JsonManagedReference
    var sentEmails: MutableList<Email> = mutableListOf()

    @OneToMany(mappedBy = "receivedByUser")
    @JsonManagedReference
    var receivedEmails: MutableList<Email> = mutableListOf()

    fun logIn() {
        this.isLoggedIn = true
    }

    fun logOut() {
        this.isLoggedIn = false
    }

    fun deleteUser() {
        this.status = false
    }

    fun getSentEmailsDTO(): MutableList<SentEmailDTO> {
        return this.sentEmails.map { it.toSentEmailDTO() }.toMutableList()
    }

    fun getReceivedEmailsDTO(): MutableList<ReceivedEmailDTO> {
        return this.receivedEmails.map { it.toReceivedEmailDTO() }.toMutableList()
    }
}
