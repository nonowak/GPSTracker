package no.nowak.gpstracker.core.password

import com.fasterxml.jackson.annotation.JsonIgnore
import no.nowak.gpstracker.core.infrastructure.converters.LocalDateTimeAttributeConverter
import no.nowak.gpstracker.core.user.User
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Password(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        @OneToOne(mappedBy = "password")
        val user: User? = null,
        var currentHash: String,

        var previousHash: String = "",

        @Convert(converter = LocalDateTimeAttributeConverter::class)
        val modifiedOn: LocalDateTime? = null,
        var lockedDuringReset: Boolean = false,
        @JsonIgnore
        var resetKey: String? = ""

) : Serializable