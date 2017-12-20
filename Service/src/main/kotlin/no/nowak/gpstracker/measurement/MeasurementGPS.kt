package no.nowak.gpstracker.measurement

import no.nowak.core.measurement.Measurement
import no.nowak.gpstracker.latLng.LatLng
import javax.persistence.*

@Entity
@DiscriminatorValue("gps")
class MeasurementGPS(
        id: Int,
        token: String,
        @Embedded
        val latLng: LatLng

) : Measurement(id = id, token = token)