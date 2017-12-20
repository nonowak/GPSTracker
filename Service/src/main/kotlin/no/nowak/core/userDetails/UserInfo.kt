package no.nowak.core.userDetails

import no.nowak.core.address.Address
import no.nowak.core.infrastructure.converters.LocalDateTimeAttributeConverter
import no.nowak.core.userDetails.dto.UserInfoDTO
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class UserInfo(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        var firstName: String? = "",
        var lastName: String? = "",

        @OneToOne(cascade = [CascadeType.ALL])
        var address: Address? = null,

        @Convert(converter = LocalDateTimeAttributeConverter::class)
        val createdOn: LocalDateTime = LocalDateTime.now()
) : Serializable {

    fun update(userInfoDTO: UserInfoDTO) {
        firstName = userInfoDTO.firstName
        lastName = userInfoDTO.lastName
        address = Address(
                countryName = userInfoDTO.countryName,
                cityName = userInfoDTO.cityName,
                streetName = userInfoDTO.streetName,
                postalCode = userInfoDTO.postalCode
        )
    }
}
