package no.nowak.gpstracker.core.password

import org.springframework.data.jpa.repository.JpaRepository

interface PasswordRepository : JpaRepository<Password, Long>{
}