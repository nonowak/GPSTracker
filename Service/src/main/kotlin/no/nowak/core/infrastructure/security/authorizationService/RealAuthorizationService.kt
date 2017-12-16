package no.nowak.core.infrastructure.security.authorizationService

import no.nowak.core.infrastructure.profileRegex.ProfileRegex
import no.nowak.core.infrastructure.security.CustomUserDetails
import no.nowak.core.user.User
import no.nowak.core.user.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("authorizationService")
@ProfileRegex("((!userFakeAuthorizationService)|(!adminFakeAuthorizationService))")
class RealAuthorizationService(val userRepository: UserRepository) : AuthorizationService {
    override fun getCurrentUser(): User {
        val emailAddress = (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).username
        return userRepository.findByEmailAddress(emailAddress) ?: throw UsernameNotFoundException("User was not found in the database.")
    }
}