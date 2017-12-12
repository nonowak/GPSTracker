package no.nowak.gpstracker.core.user

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.gpstracker.core.infrastructure.Paths.USER_PATH
import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.user.dto.UserResetPasswordDTO
import no.nowak.gpstracker.core.userDetails.UserDetails
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "User controller", tags = ["User"])
@RequestMapping(USER_PATH)
interface UserApi {

    companion object {
        const val ACTIVATION_KEY = "activationKey"
        const val RESET_PASSWORD_KEY = "resetPasswordKey"
        const val EMAIL_ADDRESS = "emailAddress"

        const val REGISTER_PATH = "/register"
        const val ACTIVATION_PATH = "$REGISTER_PATH/activate"
        const val RESET_PASSWORD_PATH = "$REGISTER_PATH/reset"
        const val LOST_PASSWORD_PATH = "$RESET_PASSWORD_PATH/lost"

    }

    @ApiOperation("Register User")
    @ApiResponses(
            ApiResponse(code = 201, message = "user.emailAddress", response = String::class),
            ApiResponse(code = 409, message = "User with this email exists", response = ServiceException::class)
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(REGISTER_PATH)
    fun registerUser(@RequestBody @Valid userRegisterDTO: UserRegisterDTO): String

    @ApiOperation("Activate User")
    @ApiResponses(
            ApiResponse(code = 200, message = "User enabled", response = String::class),
            ApiResponse(code = 404, message = "User with this activationKey not found", response = ServiceException::class),
            ApiResponse(code = 400, message = "User is enabled", response = ServiceException::class)
    )
    @GetMapping(ACTIVATION_PATH)
    fun activateUser(@RequestParam(value = ACTIVATION_KEY, required = true) activationKey: String)

    @ApiOperation("Lost Password")
    @ApiResponses(
            ApiResponse(code = 200, message = "Reset password Email sent", response = String::class)
    )
    @GetMapping(LOST_PASSWORD_PATH)
    fun lostPassword(@RequestParam(value = EMAIL_ADDRESS, required = true) emailAddress: String)

    @ApiOperation("Lost Password")
    @ApiResponses(
            ApiResponse(code = 200, message = "Password reset complete", response = String::class),
            ApiResponse(code = 409, message = "This password was used in last 3 months", response = String::class)
    )
    @PutMapping(RESET_PASSWORD_PATH)
    fun resetPassword(@RequestParam(value = RESET_PASSWORD_KEY, required = true) resetPasswordKey: String,
                      @RequestBody @Valid userResetPasswordDTO: UserResetPasswordDTO)
}