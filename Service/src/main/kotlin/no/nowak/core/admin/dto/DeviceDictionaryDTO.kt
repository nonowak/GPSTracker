package no.nowak.core.admin.dto

import no.nowak.core.device.DeviceType
import no.nowak.core.deviceDictionary.DeviceDictionary
import no.nowak.core.infrastructure.converters.LocalDateTimeAttributeConverter
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import javax.persistence.Convert

data class DeviceDictionaryDTO(
        @field:NotNull
        val deviceType: DeviceType,
        @Convert(converter = LocalDateTimeAttributeConverter::class)
        val createdOn: LocalDateTime? = null,
        val createdByEmailAddress: String? = null,
        var token: String? = null
) {
    constructor(deviceDictionary: DeviceDictionary) : this(
            deviceType = deviceDictionary.deviceType,
            createdOn = deviceDictionary.createdOn,
            createdByEmailAddress = deviceDictionary.createdBy.emailAddress,
            token = deviceDictionary.token
    )


}