package no.nowak.core.user

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.core.infrastructure.Paths.USER_PATH
import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.core.user.dto.UserRegisterDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "User Controller", tags = ["User"])
@RequestMapping(USER_PATH)
interface UserApi {

    companion object {
        const val ACTIVATION_KEY = "activationKey"

        const val REGISTER_PATH = "/register"
        const val ACTIVATION_PATH = "$REGISTER_PATH/activate"
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
}