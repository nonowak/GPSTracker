package no.nowak.gpstracker.core.deviceDictionary

import no.nowak.gpstracker.core.infrastructure.exceptions.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class DeviceDictionaryService(private val deviceDictionaryRepository: DeviceDictionaryRepository) {

    fun addDevice(deviceDictionary: DeviceDictionary): DeviceDictionary {
        if (deviceDictionaryRepository.findByToken(deviceDictionary.token) != null) throw ServiceException(HttpStatus.CONFLICT, "Device with this token already exists")
        return deviceDictionaryRepository.save(deviceDictionary)
    }

    fun getAllDevices(): List<DeviceDictionary> =
            deviceDictionaryRepository.findAll()

}