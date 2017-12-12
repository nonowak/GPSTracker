package no.nowak.gpstracker.core.userDetails

import no.nowak.gpstracker.core.infrastructure.security.authorizationService.AuthorizationService
import org.springframework.web.bind.annotation.RestController

@RestController
internal class UserDetailsController(private val authorizationService: AuthorizationService) : UserDetailsApi {

    override fun getUserDetails(): UserDetails =
            authorizationService.getCurrentUser().userDetails

}