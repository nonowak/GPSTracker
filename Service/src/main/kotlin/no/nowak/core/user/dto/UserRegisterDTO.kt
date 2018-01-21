package no.nowak.core.user.dto

import org.hibernate.validator.constraints.Email
import javax.validation.constraints.Pattern

data class UserRegisterDTO(
        @field:Email
        var emailAddress: String,

        @field:Pattern(regexp = "((?=.*\\d)(?=.*[A-Z]).{8,})")
        var password: String,
        var firstName: String? = null,
        var lastName: String? = null,
        var countryName: String? = null,
        var cityName: String? = null,
        var streetName: String? = null,
        @field:Pattern(regexp = "([0-9]{2}-[0-9]{3})", message = "Wrong postal code format")
        var postalCode: String? = null
)