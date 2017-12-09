package no.nowak.gpstracker.core.user.dto

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Pattern

data class UserRegisterDTO(
        @field:Email
        var email: String,

        @Pattern(regexp = "((?=.*\\d)(?=.*[A-Z]).{8,})", message = "Password must contains at least one number, one upper case and be 8 ")
        var password: String
)