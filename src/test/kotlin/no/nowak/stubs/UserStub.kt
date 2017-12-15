package no.nowak.stubs

import no.nowak.core.user.dto.UserRegisterDTO

class UserStub {
    companion object {
        fun getCorrectUserRegisterDTO(): UserRegisterDTO =
                UserRegisterDTO(
                        emailAddress = "test1@gmail.com",
                        password = "Test1234"
                )
    }

}