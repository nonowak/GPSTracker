package no.nowak.core.userDetails

import no.nowak.core.address.Address
import no.nowak.core.infrastructure.converters.LocalDateTimeAttributeConverter
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class UserInfo(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        var firstName: String = "",
        var lastName: String = "",

        @OneToOne
        var address: Address? = null,

        @Convert(converter = LocalDateTimeAttributeConverter::class)
        val createdOn: LocalDateTime = LocalDateTime.now()
) : Serializable
