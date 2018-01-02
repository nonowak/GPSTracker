package no.nowak.core.device

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.core.device.dto.DeviceDTO
import no.nowak.core.device.dto.DeviceWithLastMeasurementDateDTO
import no.nowak.core.infrastructure.Paths
import no.nowak.core.infrastructure.exceptions.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "Device Controller", tags = ["Device"])
@RequestMapping(Paths.DEVICES_PATH)
interface DeviceApi {

    @ApiOperation("Add Device")
    @ApiResponses(
            ApiResponse(code = 201, message = "Device added to account", response = DeviceWithLastMeasurementDateDTO::class, responseContainer = "List"),
            ApiResponse(code = 404, message = "Device not found", response = ServiceException::class),
            ApiResponse(code = 409, message = "Device is enabled", response = ServiceException::class)
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    fun addDevice(@RequestBody @Valid deviceDTO: DeviceDTO): List<DeviceWithLastMeasurementDateDTO>

    @ApiOperation("Get Devices")
    @ApiResponses(
            ApiResponse(code = 200, message = "Device added to account", response = DeviceWithLastMeasurementDateDTO::class, responseContainer = "List")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    fun getUserDevices(): List<DeviceWithLastMeasurementDateDTO>
}