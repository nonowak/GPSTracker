package no.nowak.core.infrastructure.security.authorizationService

import no.nowak.core.user.User
import no.nowak.core.user.UserRepository
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service

@Service("authorizationService")
@Profile("adminFakeAuthorizationService")
@Order(Ordered.HIGHEST_PRECEDENCE)
class AdminFakeAuthorizationService(val userRepository: UserRepository) : AuthorizationService {
    override fun getCurrentUser(): User = userRepository.findOne(2)
}