package no.nowak.gpstracker.core.infrastructure.security.authorizationService

import no.nowak.gpstracker.core.infrastructure.security.CustomUserDetails
import no.nowak.gpstracker.core.user.User
import no.nowak.gpstracker.core.user.UserRepository
import org.springframework.context.annotation.Profile
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("authorizationService")
@Profile("!fakeAuthorizationService")
class RealAuthorizationService(val userRepository: UserRepository) : AuthorizationService {
    override fun getCurrentUser(): User {
        val emailAddress = (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).username
        return userRepository.findByEmailAddress(emailAddress) ?: throw UsernameNotFoundException("User was not found in the database.")
    }
}