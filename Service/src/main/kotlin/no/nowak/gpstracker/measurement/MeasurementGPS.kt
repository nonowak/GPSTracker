package no.nowak.gpstracker.measurement

import com.google.maps.model.LatLng
import no.nowak.core.device.Device
import no.nowak.core.measurement.Measurement
import no.nowak.core.measurement.MeasurementDate
import no.nowak.gpstracker.google.GoogleAddress
import java.time.LocalTime
import javax.persistence.DiscriminatorValue
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
@DiscriminatorValue("gps")
class MeasurementGPS(
        measurementDate: MeasurementDate,
        device: Device,
        time: LocalTime,
        @Embedded
        val latLng: LatLng,
        @Embedded
        val address: GoogleAddress
) : Measurement(
        measurementDate = measurementDate,
        device = device,
        time = time
)

