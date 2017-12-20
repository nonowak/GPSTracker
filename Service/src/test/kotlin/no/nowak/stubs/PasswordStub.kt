package no.nowak.stubs

import no.nowak.core.password.dto.ResetPasswordDTO

class PasswordStub {
    companion object {
        fun getCorrectResetPasswordDTO() =
                ResetPasswordDTO(
                        emailAddress = "test@test.pl",
                        password = "Test12345"
                )
    }
}