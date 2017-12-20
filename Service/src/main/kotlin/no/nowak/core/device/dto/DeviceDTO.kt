package no.nowak.core.device.dto

import no.nowak.core.device.DeviceType
import no.nowak.core.device.Permission
import no.nowak.core.user.UserDevice
import javax.validation.constraints.Pattern

data class DeviceDTO(
        val id: Int? = null,
        val deviceType: DeviceType,
        val permission: Permission? = null,
        @field:Pattern(regexp = "([A-Za-z0-9]{4}-[A-Za-z0-9]{4})", message = "Invalid deviceDictionary")
        var token: String
) {
    constructor(userDevice: UserDevice) : this(
            id = userDevice.device.id!!,
            deviceType = userDevice.device.deviceType,
            permission = userDevice.permission,
            token = userDevice.device.deviceDictionary.token
    )
}