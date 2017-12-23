package no.nowak.gpstracker.measurement

import no.nowak.core.device.Device
import no.nowak.core.measurement.Measurement
import no.nowak.core.measurement.MeasurementDate
import no.nowak.gpstracker.latLng.LatLng
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import java.time.LocalTime
import javax.persistence.DiscriminatorValue
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
@DiscriminatorValue("gps")
class MeasurementGPS(
//        device: Device,
        time: LocalTime,
        measurementDate: MeasurementDate,
        @Embedded
        val latLng: LatLng,
        @Embedded
        val countryName: String,
        val cityName: String,
        val streetName: String
) : Measurement(
//        device = device,
        time = time,
        measurementDate = measurementDate
)