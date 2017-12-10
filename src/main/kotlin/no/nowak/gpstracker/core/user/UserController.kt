package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.infrastructure.security.authorizationService.AuthorizationService
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.userDetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class UserController(val authorizationService: AuthorizationService,
                     val userService: UserService) : UserApi {

    @GetMapping(UserApi.DETAILS_PATH)
    override fun getUserDetails(): UserDetails =
            authorizationService.getCurrentUser().userDetails


    @PostMapping(UserApi.REGISTER_PATH)
    override fun registerUser(@RequestBody @Valid userRegisterDTO: UserRegisterDTO): String
            = userService.registerUser(userRegisterDTO)

    @GetMapping(UserApi.ACTIVATION_PATH)
    override fun activateUser(@RequestParam(value = UserApi.ACTIVATION_KEY, required = true) activationKey: String) =
            userService.activateUser(activationKey)
}