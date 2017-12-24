package no.nowak.gpstracker.measurement.dto

import org.hibernate.validator.constraints.NotBlank
import org.jetbrains.annotations.NotNull

class MeasurementGPSDTO(
        @field:NotNull
        var latitude: Double,
        @field:NotNull
        var longitude: Double,
        @field:NotBlank
        var measurementTime: String
)
