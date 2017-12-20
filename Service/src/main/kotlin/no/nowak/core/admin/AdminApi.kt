package no.nowak.core.admin

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.core.admin.dto.DeviceDTO
import no.nowak.core.infrastructure.Paths.ADMIN_PATH
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
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
            ApiResponse(code = 404, message = "Invalid deviceDictionary", response = String::class),
            ApiResponse(code = 409, message = "Device with this deviceDictionary already exists", response = String::class)
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(DEVICE_PATH)
    fun addDevice(@RequestBody @Valid deviceDTO: DeviceDTO): DeviceDTO

    @ApiOperation("Get all devices")
    @ApiResponses(
            ApiResponse(code = 200, message = "Device added", response = DeviceDTO::class, responseContainer = "List")
    )
    @GetMapping(DEVICE_PATH)
    fun getAllDevices(): List<DeviceDTO>
}