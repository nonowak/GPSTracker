package no.nowak.core.device.dto

import no.nowak.core.device.Permission
import no.nowak.core.user.UserDevice
import javax.validation.constraints.NotNull

data class UserDeviceDTO(
        @NotNull
        val emailAddress: String,
        val permission: Permission = Permission.USER
) {
    constructor(userDevice: UserDevice) : this(
            emailAddress = userDevice.user.emailAddress,
            permission = userDevice.permission
    )
}