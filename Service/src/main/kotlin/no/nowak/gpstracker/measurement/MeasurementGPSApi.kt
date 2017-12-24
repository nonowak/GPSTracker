package no.nowak.gpstracker.measurement

import io.swagger.annotations.Api
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import no.nowak.core.infrastructure.Paths
import no.nowak.core.infrastructure.Paths.GPS_PATH
import no.nowak.core.infrastructure.exceptions.ServiceException
import no.nowak.gpstracker.measurement.dto.MeasurementGPSDTO
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "Measurement GPS Controller", tags = ["GPS"])
@RequestMapping(GPS_PATH)
interface MeasurementGPSApi {
    companion object {
        const val TOKEN = "deviceToken"

        const val ADD_MEASUREMENT: String = "${Paths.MEASUREMENTS_PATH}/{$TOKEN}"
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

}