package no.nowak.gpstracker.core.password

import no.nowak.gpstracker.core.password.DTO.ResetPasswordDTO
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class PasswordController(private val passwordService: PasswordService) : PasswordApi {

    companion object {
        val API = PasswordApi
    }

    @GetMapping(API.LOST_PASSWORD_PATH)
    override fun lostPassword(@RequestParam(value = API.EMAIL_ADDRESS, required = true) emailAddress: String) =
            passwordService.lostPassword(emailAddress)

    @PutMapping(API.RESET_PASSWORD_PATH)
    override fun resetPassword(@RequestParam(value = API.RESET_PASSWORD_KEY, required = true) resetPasswordKey: String,
                               @RequestBody @Valid resetPasswordDTO: ResetPasswordDTO) =
            passwordService.resetPassword(resetPasswordKey, resetPasswordDTO)
}