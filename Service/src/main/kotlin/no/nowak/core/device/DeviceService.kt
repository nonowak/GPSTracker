package no.nowak.core.device

import no.nowak.core.device.DTO.AddDeviceDTO
import no.nowak.core.device.DTO.DeviceDTO
import no.nowak.core.device.Permission.OWNER
import no.nowak.core.deviceDictionary.DeviceDictionaryService
import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.core.infrastructure.security.authorizationService.AuthorizationService
import no.nowak.core.user.User
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DeviceService(private val authorizationService: AuthorizationService,
                    private val deviceDictionaryService: DeviceDictionaryService,
                    private val deviceRepository: DeviceRepository) {
    fun addDevice(addDeviceDTO: AddDeviceDTO): Map<DeviceType, DeviceDTO> {
        val deviceDictionary = deviceDictionaryService.getByTokenAndDeviceType(addDeviceDTO.token, addDeviceDTO.deviceType)
        if (deviceDictionary.enabled) throw ServiceException(HttpStatus.CONFLICT, "Device is enabled")
        val device = Device(addDeviceDTO, deviceDictionary)
        val user = authorizationService.getCurrentUser()
        device.addUser(user, OWNER)
        save(device)
        deviceDictionaryService.enableDeviceDictionary(deviceDictionary)
        return getDeviceTypeDeviceMap(user)
    }

    fun getDeviceTypeDeviceMap(user: User) =
            user.devices.map { Pair(it.device.deviceType, DeviceDTO(it)) }.toMap()

    fun save(device: Device): Device {
        return deviceRepository.save(device)
    }
}