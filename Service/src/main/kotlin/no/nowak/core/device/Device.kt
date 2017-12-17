package no.nowak.core.device

import no.nowak.core.device.DTO.AddDeviceDTO
import no.nowak.core.deviceDictionary.DeviceDictionary
import no.nowak.core.measurement.MeasurementDate
import no.nowak.core.user.User
import no.nowak.core.user.UserDevice
import java.io.Serializable
import javax.persistence.*

@Entity
class Device(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @OneToMany(mappedBy = "device", cascade = [CascadeType.ALL])
        val users: MutableList<UserDevice> = mutableListOf(),

        @Enumerated(EnumType.STRING)
        val deviceType: DeviceType,

        @OneToOne(cascade = [CascadeType.ALL])
        val deviceDictionary: DeviceDictionary,

        @OneToMany
        val measurementDates: List<MeasurementDate> = emptyList()

) : Serializable {
    constructor(addDeviceDTO: AddDeviceDTO, deviceDictionary: DeviceDictionary) : this(
            deviceType = addDeviceDTO.deviceType,
            deviceDictionary = deviceDictionary
    )

    fun addUser(user: User, permission: Permission) {
        val userDevice = UserDevice(
                user = user,
                device = this,
                permission = permission
        )
        users.add(userDevice)
        user.devices.add(userDevice)
    }

    fun removeUser(user: User) {
        users.filter { it.user == user }.map {
            users.remove(it)
            it.user.devices.remove(it)
        }
    }
}

enum class DeviceType {
    GPSTRACKER;
}

