package no.nowak.gpstracker.measurement

import no.nowak.core.measurement.Measurement
import no.nowak.gpstracker.latLng.LatLng
import javax.persistence.DiscriminatorValue
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
@DiscriminatorValue("gps")
class MeasurementGPS(
        id: Int,
        token: String,
        @Embedded
        val latLng: LatLng,
        @Embedded
        val countryName: String,
        val cityName: String,
        val streetName: String
) : Measurement(id = id, token = token)