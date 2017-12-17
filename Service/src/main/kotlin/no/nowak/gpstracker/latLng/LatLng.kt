package no.nowak.gpstracker.latLng

import org.jetbrains.annotations.Nullable
import java.io.Serializable

data class LatLng(
        @Nullable
        var latitude: Double? = null,
        @Nullable
        var longitude: Double? = null
) : Serializable {
    constructor(lat: Double, lng: Double) : this(
            latitude = lat, longitude = lng)
}