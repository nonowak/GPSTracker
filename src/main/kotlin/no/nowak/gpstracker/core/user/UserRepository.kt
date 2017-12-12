package no.nowak.gpstracker.core.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmailAddress(emailAddress: String): User?
    fun findByActivationKey(activationKey: String): User?
}