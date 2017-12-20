package no.nowak.stubs

import no.nowak.core.admin.dto.DeviceDTO
import no.nowak.core.device.dto.AddDeviceDTO
import no.nowak.core.device.DeviceType.GPSTRACKER

class DeviceStub {
    companion object {
        fun getCorrectAdminDeviceDTO() = DeviceDTO(
                deviceType = GPSTRACKER,
                token = "1111-1112"
        )

        fun getCorrectAddDeviceDTO() = AddDeviceDTO(
                deviceType = GPSTRACKER,
                token = "1111-1111"
        )
    }
}