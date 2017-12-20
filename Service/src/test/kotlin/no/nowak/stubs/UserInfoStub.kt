package no.nowak.stubs

import no.nowak.core.address.Address
import no.nowak.core.userDetails.UserInfo
import no.nowak.core.userDetails.dto.UserInfoDTO

class UserInfoStub {
    companion object {
        fun getCorrectUpdateUserInfoDTO(): UserInfoDTO =
                UserInfoDTO(
                        firstName = "Test1",
                        lastName = "Test1",
                        countryName = "Poland",
                        cityName = "Poznan",
                        postalCode = "61-138",
                        streetName = "Piotrowo 3"
                )
    }
}