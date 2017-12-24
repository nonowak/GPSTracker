package no.nowak.gpstracker.google

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.AddressComponentType
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import com.google.maps.model.LocationType
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
        return GoogleAddress(GeocodingApi.reverseGeocode(context, latLng)
                .locationType(LocationType.ROOFTOP)
                .await()[0])
    }
}

data class GoogleAddress(
        val countryName: String = "",
        val cityName: String = "",
        val streetName: String = ""
) {
    constructor(geocodingResult: GeocodingResult) : this(
            countryName = geocodingResult.addressComponents.find { it.types.contains(AddressComponentType.COUNTRY) }?.longName ?: "",
            cityName = geocodingResult.addressComponents.find { it.types.contains(AddressComponentType.LOCALITY) }?.shortName ?: "",
            streetName = geocodingResult.addressComponents.find { it.types.any { it == AddressComponentType.STREET_ADDRESS || it == AddressComponentType.PREMISE } }?.shortName ?: ""
    )
}