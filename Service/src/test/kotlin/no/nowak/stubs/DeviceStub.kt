package no.nowak.stubs

import no.nowak.core.admin.dto.DeviceDictionaryDTO
import no.nowak.core.device.DeviceType.GPSTRACKER
import no.nowak.core.device.dto.DeviceDTO

class DeviceStub {
    companion object {
        fun getCorrectUserDeviceDTO() = DeviceDTO(
                deviceType = GPSTRACKER,
                token = "1111-1111"
        )
    }
}