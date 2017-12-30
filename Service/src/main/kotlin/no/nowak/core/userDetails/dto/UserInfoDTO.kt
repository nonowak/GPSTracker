package no.nowak.core.userDetails.dto

import no.nowak.core.user.dto.UserRegisterDTO
import no.nowak.core.userDetails.UserInfo
import javax.validation.constraints.Pattern

data class UserInfoDTO(
        var firstName: String? = null,
        var lastName: String? = null,
        var countryName: String? = null,
        var cityName: String? = null,
        var streetName: String? = null,
        @field:Pattern(regexp = "([0-9]{2}-[0-9]{3})", message = "Wrong postal code format")
        var postalCode: String? = null
) {
    constructor(userInfo: UserInfo) : this(
            firstName = userInfo.firstName,
            lastName = userInfo.lastName,
            countryName = userInfo.address?.countryName,
            cityName = userInfo.address?.cityName,
            streetName = userInfo.address?.streetName,
            postalCode = userInfo.address?.postalCode
    )
}