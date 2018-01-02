package no.nowak.core.device

import no.nowak.core.device.dto.DeviceDTO
import no.nowak.core.device.dto.DeviceWithLastMeasurementDateDTO
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class DeviceController(private val deviceService: DeviceService) : DeviceApi {

    override fun addDevice(@RequestBody @Valid deviceDTO: DeviceDTO):  List<DeviceWithLastMeasurementDateDTO> =
            deviceService.addDevice(deviceDTO)

    override fun getUserDevices():  List<DeviceWithLastMeasurementDateDTO> =
            deviceService.getCurrentUserDevices()
}