package no.nowak.core.admin.DTO

import no.nowak.core.device.Type
import no.nowak.core.deviceDictionary.DeviceDictionary
import no.nowak.core.infrastructure.converters.LocalDateTimeAttributeConverter
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import javax.persistence.Convert
import javax.validation.constraints.Pattern

data class DeviceDTO(
        @field:NotNull
        val deviceType: Type,
        @Convert(converter = LocalDateTimeAttributeConverter::class)
        val createdOn: LocalDateTime? = null,
        val createdByEmailAddress: String? = null,
        @field:Pattern(regexp = "([0-9]{4}-[0-9]{4})", message = "Invalid token")
        var token: String
) {
    constructor(deviceDictionary: DeviceDictionary) : this(
            deviceType = deviceDictionary.deviceType,
            createdOn = deviceDictionary.createdOn,
            createdByEmailAddress = deviceDictionary.createdBy.emailAddress,
            token = deviceDictionary.token
    )


}