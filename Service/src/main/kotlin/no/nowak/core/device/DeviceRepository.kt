package no.nowak.core.device

import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRepository : JpaRepository<Device, Int> {
    fun findByDeviceDictionary_Token(token: String): Device
}