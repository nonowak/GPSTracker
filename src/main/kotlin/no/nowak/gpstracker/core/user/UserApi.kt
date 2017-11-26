package no.nowak.gpstracker.core.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
interface UserApi {

    @GetMapping("/details")
    fun userDetails(): User
}