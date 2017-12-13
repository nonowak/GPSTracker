package no.nowak.gpstracker.core.userDetails

import com.fasterxml.jackson.annotation.JsonIgnore
import no.nowak.gpstracker.core.address.Address
import no.nowak.gpstracker.core.infrastructure.Tools
import no.nowak.gpstracker.core.infrastructure.converters.LocalDateTimeAttributeConverter
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
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