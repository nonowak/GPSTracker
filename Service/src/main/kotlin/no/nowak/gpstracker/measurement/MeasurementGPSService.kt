package no.nowak.gpstracker.measurement

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MeasurementGPSService(private val measurementGPSRepository: MeasurementGPSRepository) {

    @Value("\${google.key}")
    private val key: String = ""

    fun addMeasurement(measurementGPSDTO: MeasurementGPSDTO) {
        getAddressFromLatLng(LatLng(measurementGPSDTO.latitude, measurementGPSDTO.longitude))
    }

    private fun getAddressFromLatLng(latLng: LatLng) {
        val context = GeoApiContext.Builder()
                .apiKey(key)
                .build()
        val geocodingResult: List<GeocodingResult> = GeocodingApi.reverseGeocode(context, latLng).await().toList().filter { it.geometry.location == latLng }
        print(geocodingResult)
    }
}