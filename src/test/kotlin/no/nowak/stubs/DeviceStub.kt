package no.nowak.stubs

import no.nowak.core.admin.DTO.DeviceDTO
import no.nowak.core.device.Type

class DeviceStub {
    companion object {
        fun getCorrectAdminDeviceDTO() = DeviceDTO(
                deviceType = Type.GPSTRACKER,
                token = "1111-1112"
        )
    }
}