package no.nowak.core.device.DTO

import no.nowak.core.device.DeviceType
import no.nowak.core.device.Permission
import no.nowak.core.user.UserDevice

data class DeviceDTO(
        val id: Int,
        val deviceType: DeviceType,
        val permission: Permission,
        val token: String
) {
    constructor(userDevice: UserDevice) : this(
            id = userDevice.device.id!!,
            deviceType = userDevice.device.deviceType,
            permission = userDevice.permission,
            token = userDevice.device.deviceDictionary.token
    )
}