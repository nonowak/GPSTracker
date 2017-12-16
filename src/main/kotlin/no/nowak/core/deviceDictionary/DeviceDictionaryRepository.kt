package no.nowak.core.deviceDictionary

import no.nowak.core.device.DeviceType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DeviceDictionaryRepository : JpaRepository<DeviceDictionary, Int> {

    @Query("SELECT dd From DeviceDictionary dd" +
            " WHERE :token = token" +
            " AND :deviceType = deviceType")
    fun findByTokenAndDeviceType(@Param("token") token: String,
                                 @Param("deviceType") deviceType: DeviceType): DeviceDictionary?

    fun findByToken(token: String): DeviceDictionary?
}