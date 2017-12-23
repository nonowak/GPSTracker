package no.nowak.gpstracker.measurement

import no.nowak.core.device.DeviceType
import no.nowak.core.deviceDictionary.DeviceDictionaryService
import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MeasurementGPSController(private val measurementService: MeasurementGPSService,
                               private val deviceDictionaryService: DeviceDictionaryService) : MeasurementGPSApi {

    override fun addMeasurement(@PathVariable(MeasurementGPSApi.TOKEN) token: String,
                                @RequestBody @Valid measurementGPSDTO: MeasurementGPSDTO) {
//        val device = deviceDictionaryService.getByToken(token, DeviceType.GPSTRACKER)
//        if(device == null || !device.enabled) throw ServiceException(HttpStatus.FORBIDDEN, "Device not found or not enabled")
        measurementService.addMeasurement(token, measurementGPSDTO)
    }
}