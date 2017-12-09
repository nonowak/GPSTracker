package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.infrastructure.Paths.USER_PATH
import no.nowak.gpstracker.core.user.dto.UserRegisterDTO
import no.nowak.gpstracker.core.userDetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(USER_PATH)
interface UserApi {

    companion object {
        const val DETAILS_PATH = "/details"
        const val REGISTER_PATH = "/register"
    }

    @GetMapping(DETAILS_PATH)
    fun getUserDetails(): UserDetails

    @PostMapping(REGISTER_PATH)
    fun registerUser(@RequestBody @Valid userRegisterDTO: UserRegisterDTO): String
}