package no.nowak.gpstracker.measurement

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.AddressComponentType
import com.google.maps.model.AddressComponentType.*
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import com.google.maps.model.LocationType
import no.nowak.core.device.DeviceService
import no.nowak.core.measurement.MeasurementDate
import no.nowak.core.measurement.MeasurementService
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class MeasurementGPSService(private val measurementGPSRepository: MeasurementGPSRepository,
                            private val measurementService: MeasurementService,
                            private val deviceService: DeviceService) {

    @Value("\${google.key}")
    private val key: String = ""

    private var DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("uuuuMMddHHmmss")

    fun addMeasurement(token: String, measurementGPSDTO: MeasurementGPSDTO) {
        val googleAddress = getAddressFromLatLng(LatLng(measurementGPSDTO.latitude, measurementGPSDTO.longitude))
        val localDateTime = LocalDateTime.parse(measurementGPSDTO.measurementTime, DATE_TIME_FORMATER)
        val device = deviceService.getByToken(token)
        var measurementDate = measurementService.getByDate(localDateTime.toLocalDate())
        if (measurementDate == null) {
            measurementDate = MeasurementDate(
                    date = localDateTime.toLocalDate()
            )
        }
        measurementDate = measurementService.addDeviceToMeasurementDate(measurementDate, device)
        val measurementGPS = MeasurementGPS(
                latLng = no.nowak.gpstracker.latLng.LatLng(measurementGPSDTO.latitude, measurementGPSDTO.longitude),
//                device = device,
                streetName = googleAddress.streetName,
                cityName = googleAddress.cityName,
                countryName = googleAddress.countryName,
                time = localDateTime.toLocalTime(),
                measurementDate = measurementDate
        )
        measurementGPSRepository.save(measurementGPS)
    }

    private fun getAddressFromLatLng(latLng: LatLng): GoogleAddress {
        val context = GeoApiContext.Builder()
                .apiKey(key)
                .build()
        return GoogleAddress(GeocodingApi.reverseGeocode(context, latLng)
                .locationType(LocationType.ROOFTOP)
                .await()[0])
    }
}

class GoogleAddress(
        val countryName: String,
        val cityName: String,
        val streetName: String
) {
    constructor(geocodingResult: GeocodingResult) : this(
            countryName = geocodingResult.addressComponents.find { it.types.contains(COUNTRY) }?.longName ?: "",
            cityName = geocodingResult.addressComponents.find { it.types.contains(LOCALITY) }?.shortName ?: "",
            streetName = geocodingResult.addressComponents.find { it.types.any { it == STREET_ADDRESS || it == PREMISE } }?.shortName ?: ""
    )
}