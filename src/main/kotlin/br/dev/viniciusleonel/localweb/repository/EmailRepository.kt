package br.dev.viniciusleonel.localweb.repository

import br.dev.viniciusleonel.localweb.model.Email
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface EmailRepository: JpaRepository<Email, Long>{

    fun findAllBySentByUserId(sentByUser: Long, pageable: Pageable) : Page<Email>

    fun findAllByReceivedByUserId(receivedBy: Long, pageable: Pageable) : Page<Email>

}