package no.nowak.core.measurement

import no.nowak.core.device.Device
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import java.time.LocalDate
import java.time.LocalTime

@NoRepositoryBean
interface MeasurmentRepository<T : Measurement> : JpaRepository<T, Int> {
    fun findByDevice_DeviceDictionary_TokenAndTimeBetweenAndMeasurementDate_Date(token: String, startTime: LocalTime, endTime: LocalTime, date: LocalDate): T
}

interface MeasurementDateRepository : JpaRepository<MeasurementDate, Int> {
    fun findByDate(date: LocalDate): MeasurementDate?
//    fun findByDevicesAndDateBetweenOrderByDateDesc(device: Device, startDate: LocalDate, endDate: LocalDate, pageable: Pageable): List<MeasurementDate>
}