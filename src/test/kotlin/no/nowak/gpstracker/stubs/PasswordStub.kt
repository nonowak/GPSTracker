package no.nowak.gpstracker.stubs

import no.nowak.gpstracker.core.password.DTO.ResetPasswordDTO

class PasswordStub {
    companion object {
        fun getCorrectResetPasswordDTO() =
                ResetPasswordDTO(
                        emailAddress = "test@test.pl",
                        password = "Test12345"
                )
    }
}