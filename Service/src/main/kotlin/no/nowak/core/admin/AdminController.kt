package no.nowak.core.admin

import no.nowak.core.admin.dto.DeviceDTO
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