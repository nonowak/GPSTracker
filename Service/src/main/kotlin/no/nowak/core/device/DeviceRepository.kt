package no.nowak.core.device

import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRepository : JpaRepository<Device, Int> {
}