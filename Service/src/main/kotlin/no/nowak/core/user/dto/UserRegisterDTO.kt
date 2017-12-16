package no.nowak.core.user.dto

import org.hibernate.validator.constraints.Email
import javax.validation.constraints.Pattern

data class UserRegisterDTO(
        @field:Email
        var emailAddress: String,

        @field:Pattern(regexp = "((?=.*\\d)(?=.*[A-Z]).{8,})", message = "Password must contains at least one number, one upper case and be 8")
        var password: String
)