package no.nowak.gpstracker.core.password

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.gpstracker.core.infrastructure.Paths.PASSWORD_PATH
import no.nowak.gpstracker.core.password.DTO.ResetPasswordDTO
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "Password Controller", tags = ["Password"])
@RequestMapping(PASSWORD_PATH)
interface PasswordApi {
    companion object {
        const val RESET_PASSWORD_KEY = "resetKey"
        const val EMAIL_ADDRESS = "emailAddress"

        const val RESET_PASSWORD_PATH = "/reset"
        const val LOST_PASSWORD_PATH = "/lost"
    }

    @ApiOperation("Lost Password")
    @ApiResponses(
            ApiResponse(code = 200, message = "Reset password Email sent", response = String::class),
            ApiResponse(code = 404, message = "User with this email not found", response = String::class)
    )
    @GetMapping(LOST_PASSWORD_PATH)
    fun lostPassword(@RequestParam(value = EMAIL_ADDRESS, required = true) emailAddress: String)

    @ApiOperation("Reset Password")
    @ApiResponses(
            ApiResponse(code = 200, message = "Password reset complete", response = String::class),
            ApiResponse(code = 404, message = "User with this email and resetKey not found", response = String::class),
            ApiResponse(code = 409, message = "This password was used in last 3 months", response = String::class)
    )
    @PutMapping(RESET_PASSWORD_PATH)
    fun resetPassword(@RequestParam(value = RESET_PASSWORD_KEY, required = true) resetPasswordKey: String,
                      @RequestBody @Valid resetPasswordDTO: ResetPasswordDTO)
}