package no.nowak.core.device

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.core.device.dto.AddDeviceDTO
import no.nowak.core.device.dto.DeviceDTO
import no.nowak.core.infrastructure.Paths
import no.nowak.core.infrastructure.exceptions.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid

@Api(description = "Device Controller", tags = ["Device"])
@RequestMapping(Paths.DEVICES_PATH)
interface DeviceApi {
    companion object {

    }

    @ApiOperation("Add Device")
    @ApiResponses(
            ApiResponse(code = 201, message = "Device added to account", response = String::class),
            ApiResponse(code = 404, message = "Device not found", response = ServiceException::class),
            ApiResponse(code = 409, message = "Device is enabled", response = ServiceException::class)
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    fun addDevice(@RequestBody @Valid addDeviceDTO: AddDeviceDTO): Map<DeviceType, DeviceDTO>
}