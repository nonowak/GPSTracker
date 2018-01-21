package no.nowak.core.device

import no.nowak.core.device.dto.*
import no.nowak.core.infrastructure.exceptions.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class DeviceController(private val deviceService: DeviceService) : DeviceApi {

    override fun addDevice(@RequestBody @Valid deviceDTO: DeviceDTO): List<DeviceWithLastMeasurementDateDTO> =
            deviceService.addDevice(deviceDTO)

    override fun getUserDevices(): List<DeviceWithLastMeasurementDateDTO> =
            deviceService.getCurrentUserDevices()

    override fun addUserDevice(@ApiIgnore @PathVariable("id") deviceId: Device,
                               @RequestBody @Valid userDeviceDTO: UserDeviceDTO): List<UserDeviceDTO> =
            deviceService.addUserDevice(deviceId, userDeviceDTO)


    override fun getDeviceDetails(@ApiIgnore @PathVariable("id") device: Device): DeviceDetailsDTO =
            DeviceDetailsDTO(device)

    override fun updateDeviceName(@ApiIgnore @PathVariable("id") deviceId: Device,
                                  @RequestBody deviceNameDTO: DeviceNameDTO): DeviceDetailsDTO =
            deviceService.updateDeviceName(deviceId, deviceNameDTO)
}