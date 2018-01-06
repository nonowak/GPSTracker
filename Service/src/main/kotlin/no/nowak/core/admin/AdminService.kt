package no.nowak.core.admin

import no.nowak.core.admin.dto.DeviceDictionaryDTO
import no.nowak.core.device.DeviceType
import no.nowak.core.deviceDictionary.DeviceDictionary
import no.nowak.core.deviceDictionary.DeviceDictionaryService
import no.nowak.core.infrastructure.security.authorizationService.AuthorizationService
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service

@Service
class AdminService(private val deviceDictionaryService: DeviceDictionaryService,
                   private val authorizationService: AuthorizationService) {

    fun addDevice(deviceDictionaryDTO: DeviceDictionaryDTO): List<DeviceDictionaryDTO> {
        val deviceDictionary = DeviceDictionary(
                createdBy = authorizationService.getCurrentUser(),
                deviceType = deviceDictionaryDTO.deviceType,
                token = generateToken(deviceDictionaryDTO.deviceType)
        )
        DeviceDictionaryDTO(deviceDictionaryService.addDevice(deviceDictionary))
        return getAllDevices();
    }

    fun getAllDevices(): List<DeviceDictionaryDTO> =
            deviceDictionaryService.getAllDevices().map { DeviceDictionaryDTO(it) }

    private fun generateToken(deviceType: DeviceType): String {
        var randomToken: String = RandomStringUtils.random(8, true, true)
        randomToken = randomToken.substring(0, 4) + "-" + randomToken.substring(4, randomToken.length)
        if (deviceDictionaryService.getByTokenAndDeviceType(randomToken, deviceType) != null)
            generateToken(deviceType)
        return randomToken

    }
}