package no.nowak.core.measurement

import no.nowak.core.device.Device
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MeasurementService(private val measurementDateRepository: MeasurementDateRepository) {

    fun getByDate(localDate: LocalDate) =
            measurementDateRepository.findByDate(localDate)

    fun addDeviceToMeasurementDate(measurementDate: MeasurementDate, device: Device): MeasurementDate {
        if (!measurementDate.devices.contains(device))
            measurementDate.devices.add(device)
        return measurementDate
    }

    fun getTopDateByDevice(device: Device, pageable: PageRequest = PageRequest(0, 1)): Page<MeasurementDate> {
        return measurementDateRepository.findTopByDevice(device, pageable)
    }

//    fun getByDeviceAndDateBetween(device: Device, startDate: LocalDate, endDate: LocalDate, pageable: Pageable): List<MeasurementDate> =
//            measurementDateRepository.findByDevicesAndDateBetweenOrderByDateDesc(device, startDate, endDate, pageable)

}