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
}

interface MeasurementDateRepository : JpaRepository<MeasurementDate, Int> {
    fun findByDate(date: LocalDate): MeasurementDate?

    @Query("SELECT m.date FROM MeasurementDate m" +
            " JOIN m.devices d ON d = :device" +
            " ORDER By m.date DESC")
    fun findTopDateByDevice(@Param("device") device: Device,
                            pageable: Pageable): Page<LocalDate>
}