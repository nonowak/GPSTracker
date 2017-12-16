package no.nowak.core.admin

import no.nowak.core.admin.DTO.DeviceDTO
import no.nowak.core.deviceDictionary.DeviceDictionary
import no.nowak.core.deviceDictionary.DeviceDictionaryService
import no.nowak.core.infrastructure.security.authorizationService.AuthorizationService
import org.springframework.stereotype.Service

@Service
class AdminService(private val deviceDictionaryService: DeviceDictionaryService,
                   private val authorizationService: AuthorizationService) {

    fun addDevice(deviceDTO: DeviceDTO): DeviceDTO {
        val deviceDictionary = DeviceDictionary(
                createdBy = authorizationService.getCurrentUser(),
                deviceType = deviceDTO.deviceType,
                token = deviceDTO.token
        )
        return DeviceDTO(deviceDictionaryService.addDevice(deviceDictionary))
    }

    fun getAllDevices(): List<DeviceDTO> =
            deviceDictionaryService.getAllDevices().map { DeviceDTO(it) }
}