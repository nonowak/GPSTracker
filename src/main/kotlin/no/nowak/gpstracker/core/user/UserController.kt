package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.infrastructure.security.authorizationService.AuthorizationService
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val authorizationService: AuthorizationService) : UserApi {

    override fun userDetails(): User
        = authorizationService.getCurrentUser()

}