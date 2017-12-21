package no.nowak.core.measurement

import no.nowak.core.infrastructure.converters.LocalDateAttributeConverter
import no.nowak.core.infrastructure.converters.LocalTimeAttributeConverter
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "measurement_type")
open class Measurement(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val token: String
)

@Entity
data class MeasurementTime(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Convert(converter = LocalTimeAttributeConverter::class)
        val time: LocalTime = LocalTime.now(),
        @OneToOne
        val measurement: Measurement
)

@Entity
data class MeasurementDate(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,
        @Convert(converter = LocalDateAttributeConverter::class)
        @Column(unique = true)
        val date: LocalDate,
        @OneToMany
        val measurementTime: List<MeasurementTime>
)
