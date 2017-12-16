package no.nowak.core.infrastructure.security

import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.core.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component("userDetailsService")
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmailAddress(username) ?: throw UsernameNotFoundException("User was not found in the database")
        if (user.password.lockedDuringReset) throw ServiceException(HttpStatus.UNAUTHORIZED, "User is locked")
        if (!user.enabled) throw ServiceException(HttpStatus.UNAUTHORIZED, "User not enabled")
        return CustomUserDetails(user)
    }
}