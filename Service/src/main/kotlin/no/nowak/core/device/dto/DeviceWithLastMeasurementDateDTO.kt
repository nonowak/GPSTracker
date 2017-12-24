package no.nowak.core.device.dto

import no.nowak.core.device.Permission
import no.nowak.core.measurement.MeasurementDate
import no.nowak.core.user.UserDevice
import java.time.LocalDate

data class DeviceWithLastMeasurementDateDTO(
        var id: Int?,
        var name: String,
        var lastMeasurementDate: LocalDate?,
        var permission: Permission
) {
    constructor(userDevice: UserDevice, localDate: LocalDate?): this(
            id = userDevice.device.id,
            name = userDevice.device.name,
            lastMeasurementDate = localDate,
            permission = userDevice.permission
    )
}