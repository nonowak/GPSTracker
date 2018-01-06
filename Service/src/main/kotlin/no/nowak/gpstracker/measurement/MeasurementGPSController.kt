package no.nowak.gpstracker.measurement

import no.nowak.core.device.Device
import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.core.infrastructure.security.authorizationService.AuthorizationService
import no.nowak.core.measurement.DTO.MeasurementFirstAndLastDateDTO
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import no.nowak.gpstracker.measurement.dto.MeasurementResponseDTO
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate
import javax.validation.Valid

@RestController
class MeasurementGPSController(private val measurementService: MeasurementGPSService,
                               private val authorizationService: AuthorizationService) : MeasurementGPSApi {

    override fun addMeasurement(@PathVariable(MeasurementGPSApi.TOKEN) token: String,
                                @RequestBody @Valid measurementGPSDTO: MeasurementGPSDTO) {
        measurementService.addMeasurement(token, measurementGPSDTO)
    }

    override fun getMeasurementsBetweenDates(@PathVariable(MeasurementGPSApi.DEVICE_ID) @ApiIgnore deviceId: Device,
                                             @RequestParam(MeasurementGPSApi.START_DATE, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate?,
                                             @RequestParam(MeasurementGPSApi.END_DATE, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate?): List<MeasurementResponseDTO>? {
        if (authorizationService.getCurrentUser().devices.none { it.device == deviceId }) throw ServiceException(HttpStatus.UNAUTHORIZED, "User not assigned to this device")
        return if (startDate == null || endDate == null)
            measurementService.findTopMeasurements(deviceId)
        else
            measurementService.findMeasurementsBetween(deviceId, startDate, endDate)
    }

    override fun getMeasurementsPeriod(@PathVariable(MeasurementGPSApi.DEVICE_ID) @ApiIgnore deviceId: Device): MeasurementFirstAndLastDateDTO? {
        if (authorizationService.getCurrentUser().devices.none { it.device == deviceId }) throw ServiceException(HttpStatus.UNAUTHORIZED, "User not assigned to this device")
        return measurementService.findFirstAndLastMeasurementDate(deviceId)
    }
}