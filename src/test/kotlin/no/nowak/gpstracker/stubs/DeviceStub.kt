package no.nowak.gpstracker.stubs

import no.nowak.gpstracker.core.admin.DTO.DeviceDTO
import no.nowak.gpstracker.core.device.Type

class DeviceStub {
    companion object {
        fun getCorrectAdminDeviceDTO() = DeviceDTO(
                deviceType = Type.GPSTRACKER,
                token = "1111-1112"
        )
    }
}