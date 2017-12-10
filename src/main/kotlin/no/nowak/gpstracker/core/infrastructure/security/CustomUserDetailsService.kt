package no.nowak.gpstracker.core.infrastructure.security

import no.nowak.gpstracker.core.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException
import org.springframework.stereotype.Component

@Component("userDetailsService")
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmailAddress(username) ?: throw UsernameNotFoundException("User was not found in the database")
        if (!user.enabled) throw UnauthorizedUserException("User not enabled")
        return CustomUserDetails(user)
    }
}