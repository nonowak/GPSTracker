package no.nowak.gpstracker.measurement

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import com.google.maps.model.LocationType
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class MeasurementGPSService(private val measurementGPSRepository: MeasurementGPSRepository) {

    @Value("\${google.key}")
    private val key: String = ""

    private var DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("uuuuMMddHHmmss")

    fun addMeasurement(measurementGPSDTO: MeasurementGPSDTO) {
        getAddressFromLatLng(LatLng(measurementGPSDTO.latitude, measurementGPSDTO.longitude))
        val localDateTime = LocalDateTime.parse(measurementGPSDTO.measurementTime, DATE_TIME_FORMATER)
        print(localDateTime)
    }

    private fun getAddressFromLatLng(latLng: LatLng) {
//        val context = GeoApiContext.Builder()
//                .apiKey(key)
//                .build()
//        val geocodingResult: List<GeocodingResult> = GeocodingApi.reverseGeocode(context, latLng)
//                .locationType(LocationType.ROOFTOP)
//                .await().toList()
//        print(geocodingResult)
    }
}