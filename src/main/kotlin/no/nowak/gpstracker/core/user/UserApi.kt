package no.nowak.gpstracker.core.user

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.gpstracker.core.infrastructure.Paths.USER_PATH
import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.userDetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "User controller", value = "")
@RequestMapping(USER_PATH)
interface UserApi {

    companion object {
        const val ACTIVATION_KEY = "activationKey"

        const val DETAILS_PATH = "/details"
        const val REGISTER_PATH = "/register"
        const val ACTIVATION_PATH = "$REGISTER_PATH/activate"

    }

    @ApiOperation("Get User Details")
    @ApiResponse(code = 200, message = "userDetails", response = UserDetails::class)
    @GetMapping(DETAILS_PATH)
    fun getUserDetails(): UserDetails

    @ApiOperation("Register User")
    @ApiResponses(
            ApiResponse(code = 200, message = "user.emailAddress", response = String::class),
            ApiResponse(code = 409, message = "User with this email exists", response = ServiceException::class)
    )
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
}