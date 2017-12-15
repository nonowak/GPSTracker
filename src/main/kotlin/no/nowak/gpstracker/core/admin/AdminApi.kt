package no.nowak.gpstracker.core.admin

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import jdk.internal.org.objectweb.asm.TypeReference
import no.nowak.gpstracker.core.admin.DTO.DeviceDTO
import no.nowak.gpstracker.core.infrastructure.Paths
import no.nowak.gpstracker.core.infrastructure.Paths.ADMIN_PATH
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

@Api(description = "Admin Controller", tags = ["Admin"])
@RequestMapping(ADMIN_PATH)
interface AdminApi {

    companion object {
        const val DEVICE_PATH = "/devices"
    }

    @ApiOperation("Add new device")
    @ApiResponses(
            ApiResponse(code = 201, message = "Device added", response = DeviceDTO::class),
            ApiResponse(code = 404, message = "Invalid token", response = String::class),
            ApiResponse(code = 409, message = "Device with this token already exists", response = String::class)
    )
    @PostMapping(DEVICE_PATH)
    fun addDevice(@RequestBody @Valid deviceDTO: DeviceDTO): DeviceDTO

    @ApiOperation("Get all devices")
    @ApiResponses(
            ApiResponse(code = 200, message = "Device added", response = DeviceDTO::class, responseContainer = "List")
    )
    @GetMapping(DEVICE_PATH)
    fun getAllDevices(): List<DeviceDTO>
}