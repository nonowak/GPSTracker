package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.user.dto.UserResetPasswordDTO
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

    @GetMapping(API.LOST_PASSWORD_PATH)
    override fun lostPassword(@RequestParam(value = API.EMAIL_ADDRESS, required = true) emailAddress: String) =
            userService.lostPassword(emailAddress)

    @PutMapping(API.RESET_PASSWORD_PATH)
    override fun resetPassword(@RequestParam(value = API.RESET_PASSWORD_KEY, required = true) resetPasswordKey: String,
                               @RequestBody @Valid userResetPasswordDTO: UserResetPasswordDTO) =
            userService.resetPassword(resetPasswordKey, userResetPasswordDTO)
}