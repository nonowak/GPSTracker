package no.nowak.gpstracker.measurement

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.core.device.Device
import no.nowak.core.infrastructure.Paths
import no.nowak.core.infrastructure.Paths.GPS_PATH
import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.core.measurement.DTO.MeasurementFirstAndLastDateDTO
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import no.nowak.gpstracker.measurement.dto.MeasurementResponseDTO
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate
import javax.validation.Valid

@Api(description = "Measurement GPS Controller", tags = ["GPS"])
@RequestMapping(GPS_PATH)
interface MeasurementGPSApi {
    companion object {
        const val TOKEN = "deviceToken"
        const val DEVICE_ID = "deviceId"
        const val START_DATE = "startDate"
        const val END_DATE = "endDate"

        const val ADD_MEASUREMENT: String = "${Paths.MEASUREMENTS_PATH}/{$TOKEN}"
        const val GET_MEASUREMENTS: String = "${Paths.MEASUREMENTS_PATH}/{$DEVICE_ID}"
        const val GET_FIRST_AND_LAST_DATES: String = "${Paths.MEASUREMENTS_PATH}/{$DEVICE_ID}/dates"
    }

    @ApiResponses(
            ApiResponse(code = 201, message = "Measurement added", response = String::class),
            ApiResponse(code = 401, message = "Unauthorized", response = ServiceException::class)
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(ADD_MEASUREMENT)
    @PreAuthorize("@deviceDictionaryService.getByToken(#token).enabled == true")
    fun addMeasurement(@PathVariable(TOKEN) token: String,
                       @RequestBody @Valid measurementGPSDTO: MeasurementGPSDTO)

    @ApiImplicitParam(name = "deviceId", value = "Device Id", paramType = "path", dataType = "int")
    @ApiResponses(
            ApiResponse(code = 200, message = "Measurements", response = MeasurementResponseDTO::class, responseContainer = "List"),
            ApiResponse(code = 401, message = "User not assigned to this device", response = String::class)
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(GET_MEASUREMENTS)
    fun getMeasurementsBetweenDates(@PathVariable(DEVICE_ID) @ApiIgnore deviceId: Device,
                                    @RequestParam(START_DATE, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate?,
                                    @RequestParam(END_DATE, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate?): List<MeasurementResponseDTO>?

    @ApiImplicitParam(name = "deviceId", value = "Device Id", paramType = "path", dataType = "int")
    @ApiResponses(
            ApiResponse(code = 200, message = "Measurements", response = MeasurementFirstAndLastDateDTO::class),
            ApiResponse(code = 401, message = "User not assigned to this device", response = String::class)
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(GET_FIRST_AND_LAST_DATES)
    fun getMeasurementsPeriod(@PathVariable(DEVICE_ID) @ApiIgnore deviceId: Device): MeasurementFirstAndLastDateDTO?

}