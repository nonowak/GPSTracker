package no.nowak.gpstracker.stubs

import no.nowak.gpstracker.core.user.dto.UserRegisterDTO

class UserStub {
    companion object {
        fun getCorrectUserRegisterDTO(): UserRegisterDTO =
                UserRegisterDTO(
                        email = "test1@gmail.com",
                        password = "Test1234"
                )
    }

}