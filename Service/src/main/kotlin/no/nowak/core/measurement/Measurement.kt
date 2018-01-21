 package no.nowak.core.measurement

import no.nowak.core.device.Device
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
        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val device: Device,
        @Convert(converter = LocalTimeAttributeConverter::class)
        val time: LocalTime,
        @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        val measurementDate: MeasurementDate
)
@Entity
data class MeasurementDate(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        @Convert(converter = LocalDateAttributeConverter::class)
        @Column(unique = true)
        val date: LocalDate,
        @OneToMany(mappedBy = "measurementDate", fetch = FetchType.LAZY)
        val measurements: List<Measurement> = emptyList(),
        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var devices: MutableList<Device> = mutableListOf()
)
