package br.dev.viniciusleonel.localweb.repository

import br.dev.viniciusleonel.localweb.model.User
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable

@Repository
interface UserRepository: JpaRepository<User, Long> {

    override fun findAll(pageable: Pageable ) : Page<User>

    fun existsByUsername(username: String) : Boolean

    fun existsByEmail(email: String) : Boolean

    fun findByUsername(username: String) : User?

    fun findByEmail(email: String) : User?

}