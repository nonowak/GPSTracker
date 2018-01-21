package no.nowak.core.device.dto

import no.nowak.core.device.Device

data class DeviceDetailsDTO(
        val name: String,
        val users: List<UserDeviceDTO>
) {
    constructor(device: Device) : this(
            name = device.name,
            users = device.users.map { UserDeviceDTO(it.user.emailAddress, it.permission) }
    )
}