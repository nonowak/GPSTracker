package no.nowak.gpstracker.google

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GoogleApiService {
    @Value("\${google.key}")
    private val key: String = ""

    fun getAddressFromLatLng(latLng: LatLng): GoogleAddress {
        val context = GeoApiContext.Builder()
                .apiKey(key)
                .build()


        val rooftopGeocodingResult = GeocodingApi.reverseGeocode(context, latLng)
                .locationType(LocationType.ROOFTOP)
                .await().firstOrNull()

        val geometricCenterGeocodingResult = GeocodingApi.reverseGeocode(context, latLng)
                .locationType(LocationType.GEOMETRIC_CENTER)
                .await().firstOrNull()

        return when {
            rooftopGeocodingResult != null -> GoogleAddress(rooftopGeocodingResult)
            geometricCenterGeocodingResult != null -> GoogleAddress(geometricCenterGeocodingResult)
            else -> GoogleAddress(GeocodingApi.reverseGeocode(context, latLng)
                    .locationType(LocationType.APPROXIMATE)
                    .await().firstOrNull())
        }
    }
}

data class GoogleAddress(
        val countryName: String = "",
        val cityName: String = "",
        val streetName: String = ""
) {
    constructor(geocodingResult: GeocodingResult?) : this(
            countryName = geocodingResult?.addressComponents?.find { it.types.contains(AddressComponentType.COUNTRY) }?.longName ?: "",
            cityName = geocodingResult?.addressComponents?.find { it.types.contains(AddressComponentType.LOCALITY) }?.shortName ?: "",
            streetName = geocodingResult?.addressComponents?.find { it.types.any { it == AddressComponentType.ROUTE || it == AddressComponentType.PREMISE } }?.shortName ?: ""
    )
}