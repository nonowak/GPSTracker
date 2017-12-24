package no.nowak.core.device

import no.nowak.core.device.Permission.OWNER
import no.nowak.core.device.dto.DeviceDTO
import no.nowak.core.device.dto.DeviceWithLastMeasurementDateDTO
import no.nowak.core.deviceDictionary.DeviceDictionaryService
import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.core.infrastructure.security.authorizationService.AuthorizationService
import no.nowak.core.measurement.MeasurementService
import no.nowak.core.user.User
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DeviceService(private val authorizationService: AuthorizationService,
                    private val deviceDictionaryService: DeviceDictionaryService,
                    private val deviceRepository: DeviceRepository,
                    private val measurementService: MeasurementService) {
    fun addDevice(deviceDTO: DeviceDTO): Map<DeviceType, MutableList<DeviceWithLastMeasurementDateDTO>> {
        val deviceDictionary = deviceDictionaryService.getByTokenAndDeviceType(deviceDTO.token, deviceDTO.deviceType) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Device not found")
        if (deviceDictionary.enabled) throw ServiceException(HttpStatus.CONFLICT, "Device is enabled")
        val device = Device(deviceDTO, deviceDictionary)
        val user = authorizationService.getCurrentUser()
        device.addUser(user, OWNER)
        save(device)
        deviceDictionaryService.enableDeviceDictionary(deviceDictionary)
        return getDeviceTypeDeviceLastMeasurementMap(user)
    }

    fun getCurrentUserDevices(): Map<DeviceType, MutableList<DeviceWithLastMeasurementDateDTO>> =
            getDeviceTypeDeviceLastMeasurementMap(authorizationService.getCurrentUser())

    fun getDeviceTypeDeviceLastMeasurementMap(user: User): Map<DeviceType, MutableList<DeviceWithLastMeasurementDateDTO>> {
        val result: MutableMap<DeviceType, MutableList<DeviceWithLastMeasurementDateDTO>> = mutableMapOf()
        user.devices.forEach {
            if (result.containsKey(it.device.deviceType))
                result[it.device.deviceType]?.add(DeviceWithLastMeasurementDateDTO(it, measurementService.getTopDateByDevice(it.device).firstOrNull()?.date))
            else
                result.put(it.device.deviceType, mutableListOf(DeviceWithLastMeasurementDateDTO(it, measurementService.getTopDateByDevice(it.device).firstOrNull()?.date)))
        }
        return result.toMap()
    }

    fun save(device: Device): Device =
            deviceRepository.save(device)

    fun getByToken(token: String): Device =
            deviceRepository.findByDeviceDictionary_Token(token)

}