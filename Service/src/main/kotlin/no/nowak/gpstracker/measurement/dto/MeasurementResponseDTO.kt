package no.nowak.gpstracker.measurement.dto

import com.google.maps.model.LatLng
import no.nowak.gpstracker.google.GoogleAddress
import no.nowak.gpstracker.measurement.MeasurementGPS
import java.time.LocalDate
import java.time.LocalTime

data class MeasurementResponseDTO(
        val address: GoogleAddress,
        val latLng: LatLng,
        val measurementTime: LocalTime,
        val measurementDate: LocalDate
) {
    constructor(measurementGPS: MeasurementGPS) : this(
            latLng = measurementGPS.latLng,
            address = measurementGPS.address,
            measurementTime = measurementGPS.time,
            measurementDate = measurementGPS.measurementDate.date
    )
}