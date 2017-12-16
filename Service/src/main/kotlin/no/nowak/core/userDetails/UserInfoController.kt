package no.nowak.core.userDetails

import no.nowak.core.infrastructure.security.authorizationService.AuthorizationService
import org.springframework.web.bind.annotation.RestController

@RestController
internal class UserInfoController(private val authorizationService: AuthorizationService) : UserInfoApi {

    override fun getUserInfo(): UserInfo =
            authorizationService.getCurrentUser().userInfo

}