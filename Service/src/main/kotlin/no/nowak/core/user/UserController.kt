package no.nowak.core.user

import no.nowak.core.user.dto.UserRegisterDTO
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class UserController(private val userService: UserService) : UserApi {

    companion object {
        val API = UserApi
    }

    @PostMapping(API.REGISTER_PATH)
    override fun registerUser(@RequestBody @Valid userRegisterDTO: UserRegisterDTO): String =
            userService.registerUser(userRegisterDTO)

    @GetMapping(API.ACTIVATION_PATH)
    override fun activateUser(@RequestParam(value = API.ACTIVATION_KEY, required = true) activationKey: String) =
            userService.activateUser(activationKey)

}