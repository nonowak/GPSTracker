package no.nowak.gpstracker.core.user

import org.springframework.web.bind.annotation.RestController

@RestController("/user")
interface UserApi {

    fun login()
}