package no.nowak.core.measurement

import no.nowak.core.device.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import java.time.LocalDate
import java.time.LocalTime

@NoRepositoryBean
interface MeasurmentRepository<T : Measurement> : JpaRepository<T, Int> {

    @Query("SELECT m FROM #{#entityName} AS m WHERE m.device = ?1 ")
    fun findByDevice(device: Device): T
}

interface MeasurementDateRepository : JpaRepository<MeasurementDate, Int> {
    fun findByDate(date: LocalDate): MeasurementDate?
}