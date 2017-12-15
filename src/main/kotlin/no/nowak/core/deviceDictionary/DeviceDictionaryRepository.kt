package no.nowak.core.deviceDictionary

import no.nowak.core.device.Type
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DeviceDictionaryRepository : JpaRepository<DeviceDictionary, Int> {

    @Query("SELECT dd From DeviceDictionary dd" +
            " WHERE pgp_sym_decrypt(:token, 'gpsTracker123') = token" +
            " AND :type = type" +
            " AND :enabled = enabled")
    fun findByTokenAndTypeAndEnabled(@Param("token") token: String,
                                     @Param("type") type: Type,
                                     @Param("enabled") enabled: Boolean): DeviceDictionary

    fun findByToken(token: String): DeviceDictionary?
}