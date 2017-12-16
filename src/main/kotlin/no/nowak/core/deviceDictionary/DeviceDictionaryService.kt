package no.nowak.core.deviceDictionary

import no.nowak.core.device.DeviceType
import no.nowak.core.infrastructure.exceptions.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DeviceDictionaryService(private val deviceDictionaryRepository: DeviceDictionaryRepository) {

    fun addDevice(deviceDictionary: DeviceDictionary): DeviceDictionary {
        if (deviceDictionaryRepository.findByToken(deviceDictionary.token) != null) throw ServiceException(HttpStatus.CONFLICT, "Device with this deviceDictionary already exists")
        return save(deviceDictionary)
    }

    fun enableDeviceDictionary(deviceDictionary: DeviceDictionary) {
        deviceDictionary.enabled = true
        save(deviceDictionary)
    }

    fun getAllDevices(): List<DeviceDictionary> =
            deviceDictionaryRepository.findAll()

    fun getByTokenAndDeviceType(token: String, deviceType: DeviceType) =
            deviceDictionaryRepository.findByTokenAndDeviceType(token, deviceType) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Device not found")

    fun save(deviceDictionary: DeviceDictionary): DeviceDictionary =
            deviceDictionaryRepository.save(deviceDictionary)
}