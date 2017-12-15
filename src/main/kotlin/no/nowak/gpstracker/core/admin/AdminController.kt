package no.nowak.gpstracker.core.admin

import no.nowak.gpstracker.core.admin.DTO.DeviceDTO
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AdminController(private val adminService: AdminService) : AdminApi {

    override fun addDevice(@RequestBody @Valid deviceDTO: DeviceDTO): DeviceDTO =
            adminService.addDevice(deviceDTO)

    override fun getAllDevices(): List<DeviceDTO> =
            adminService.getAllDevices()
}