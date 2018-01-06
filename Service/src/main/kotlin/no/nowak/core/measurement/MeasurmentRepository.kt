package no.nowak.core.measurement

import no.nowak.core.device.Device
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.time.LocalTime

@NoRepositoryBean
interface MeasurmentRepository<T : Measurement> : JpaRepository<T, Int> {
    fun findByDevice_DeviceDictionary_TokenAndTimeBetweenAndMeasurementDate_Date(token: String, startTime: LocalTime, endTime: LocalTime, date: LocalDate): T
    fun findByDeviceAndTimeBetweenAndMeasurementDate(device: Device, startTime: LocalTime, endTime: LocalTime, measurementDate: MeasurementDate): List<T>
    fun findByDeviceAndTimeBetweenAndMeasurementDate_DateBetween(device: Device, startTime: LocalTime, endTime: LocalTime, startDate: LocalDate, endDate: LocalDate): List<T>
}

interface MeasurementDateRepository : JpaRepository<MeasurementDate, Int> {
    fun findByDate(date: LocalDate): MeasurementDate?

    @Query("SELECT m FROM MeasurementDate m" +
            " JOIN m.devices d ON d = :device" +
            " ORDER By m.date DESC")
    fun findTopByDevice(@Param("device") device: Device,
                        pageable: Pageable): Page<MeasurementDate>

    @Query("SELECT min(m.date) FROM MeasurementDate m" +
            " JOIN m.devices d ON d = :device")
    fun findEarliestDateByDevice(@Param("device") device: Device): LocalDate

    @Query("SELECT max(m.date) FROM MeasurementDate m" +
            " JOIN m.devices d ON d = :device")
    fun findLastDateByDevice(@Param("device") device: Device): LocalDate
}