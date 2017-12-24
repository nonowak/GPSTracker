package no.nowak.core.deviceDictionary

import no.nowak.core.device.DeviceType
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceDictionaryRepository : JpaRepository<DeviceDictionary, Int> {


    fun findByTokenAndDeviceType(token: String, deviceType: DeviceType): DeviceDictionary?
    fun findByToken(token: String): DeviceDictionary?
}