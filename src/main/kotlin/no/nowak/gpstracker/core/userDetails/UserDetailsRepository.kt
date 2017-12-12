package no.nowak.gpstracker.core.userDetails

import org.springframework.data.jpa.repository.JpaRepository

interface UserDetailsRepository : JpaRepository<UserDetails, Int>
