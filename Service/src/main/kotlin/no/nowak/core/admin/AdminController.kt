package no.nowak.core.admin

import no.nowak.core.admin.dto.DeviceDictionaryDTO
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AdminController(private val adminService: AdminService) : AdminApi {

    override fun addDevice(@RequestBody @Valid deviceDictionaryDTO: DeviceDictionaryDTO): DeviceDictionaryDTO =
            adminService.addDevice(deviceDictionaryDTO)

    override fun getAllDevices(): List<DeviceDictionaryDTO> =
            adminService.getAllDevices()
}