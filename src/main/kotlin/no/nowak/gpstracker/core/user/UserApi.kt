package no.nowak.gpstracker.core.user

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.gpstracker.core.infrastructure.Paths.USER_PATH
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.userDetails.UserDetails
import org.hibernate.service.spi.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "User controller")
@RequestMapping(USER_PATH)
interface UserApi {

    companion object {
        const val DETAILS_PATH = "/details"
        const val REGISTER_PATH = "/register"

        const val ACTIVATION_KEY = "activationKey"
    }

    @ApiResponse(code = 200, message = "userDetails", response = UserDetails::class)
    @GetMapping(DETAILS_PATH)
    fun getUserDetails(): UserDetails

    @ApiResponses(
            ApiResponse(code = 200, message = "User enabled", response = String::class),
            ApiResponse(code = 409, message = "User not found", response = ServiceException::class)
    )
    @PostMapping(REGISTER_PATH)
    fun registerUser(@RequestBody @Valid userRegisterDTO: UserRegisterDTO): String

    @ApiResponses(
        ApiResponse(code = 200, message = "User Enabled", response = String::class),
        ApiResponse(code = 404, message = "User with this activationKey not found", response = ServiceException::class)
    )
    @GetMapping(REGISTER_PATH)
    fun activateUser(@RequestParam(value = ACTIVATION_KEY, required = true) activationKey: String)
}