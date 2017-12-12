package no.nowak.gpstracker.core.password

import org.springframework.data.jpa.repository.JpaRepository

interface PasswordRepository : JpaRepository<Password, Int>{
    fun findByUser_emailAddress(emailAddress: String): Password?
    fun findByResetKeyAndUser_emailAddress(resetKey: String, emailAddress: String): Password?
}