package no.nowak.gpstracker.core.infrastructure.security.authorizationService

import no.nowak.gpstracker.core.user.User
import no.nowak.gpstracker.core.user.UserRepository
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service

@Service("authorizationService")
@Profile("fakeAuthorizationService")
@Order(Ordered.HIGHEST_PRECEDENCE)
class FakeAuthorizationService(val userRepository: UserRepository) : AuthorizationService {
    override fun getCurrentUser(): User = userRepository.findOne(1)
}