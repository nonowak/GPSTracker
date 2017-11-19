package no.nowak.gpstracker.core.user.userDetails

import org.springframework.data.jpa.repository.JpaRepository

interface UserDetailsRepository : JpaRepository<UserDetails, Long>{
}