package no.nowak.stubs

import no.nowak.core.admin.dto.DeviceDictionaryDTO
import no.nowak.core.device.DeviceType

class DeviceDictionaryStub {
    companion object {
        fun getCorrectDeviceDTO() = DeviceDictionaryDTO(
                deviceType = DeviceType.GPSTRACKER
        )
    }
}