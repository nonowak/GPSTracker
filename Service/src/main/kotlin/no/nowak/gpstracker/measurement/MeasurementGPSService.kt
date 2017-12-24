package no.nowak.gpstracker.measurement

import com.google.maps.model.LatLng
import no.nowak.core.device.DeviceService
import no.nowak.core.measurement.MeasurementDate
import no.nowak.core.measurement.MeasurementService
import no.nowak.gpstracker.google.GoogleApiService
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class MeasurementGPSService(private val measurementGPSRepository: MeasurementGPSRepository,
                            private val measurementService: MeasurementService,
                            private val deviceService: DeviceService,
                            private val googleApiService: GoogleApiService) {

    private var DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuuMMddHHmmss")

    fun addMeasurement(token: String, measurementGPSDTO: MeasurementGPSDTO) {
        val latLng = LatLng(measurementGPSDTO.latitude, measurementGPSDTO.longitude)
        val googleAddress = googleApiService.getAddressFromLatLng(latLng)
        val measurementDateTime = LocalDateTime.parse(measurementGPSDTO.measurementTime, DATE_TIME_FORMATTER)
        val device = deviceService.getByToken(token)
        var measurementDate = measurementService.getByDate(measurementDateTime.toLocalDate())
        if (measurementDate == null) {
            measurementDate = MeasurementDate(
                    date = measurementDateTime.toLocalDate()
            )
        }
        measurementDate = measurementService.addDeviceToMeasurementDate(measurementDate, device)
        val measurementGPS = MeasurementGPS(
                latLng = LatLng(measurementGPSDTO.latitude, measurementGPSDTO.longitude),
                device = device,
                address = googleAddress,
                measurementDate = measurementDate,
                time = measurementDateTime.toLocalTime()
        )
        measurementGPSRepository.save(measurementGPS)
    }
}