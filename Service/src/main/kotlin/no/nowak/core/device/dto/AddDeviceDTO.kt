package no.nowak.core.device.dto

import no.nowak.core.device.DeviceType
import javax.validation.constraints.Pattern

data class AddDeviceDTO(
        @field:Pattern(regexp = "([0-9]{4}-[0-9]{4})", message = "Invalid deviceDictionary")
        var token: String,
        val deviceType: DeviceType
)