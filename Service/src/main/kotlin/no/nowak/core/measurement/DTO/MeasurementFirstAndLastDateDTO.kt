package no.nowak.core.measurement.DTO

import java.time.LocalDate

data class MeasurementFirstAndLastDateDTO(
        var earliestDate: LocalDate? = null,
        var lastDate: LocalDate? = null
)