package no.nowak.core.user

import no.nowak.core.device.Device
import no.nowak.core.device.Permission
import java.io.Serializable
import javax.persistence.*

@Entity
data class UserDevice(

        @EmbeddedId
        val userDeviceId: UserDeviceId,

        @ManyToOne
        @MapsId("userId")
        @JoinColumn(name = "userId")
        var user: User,

        @ManyToOne
        @MapsId("deviceId")
        @JoinColumn(name = "deviceId")
        var device: Device,

        @Enumerated(EnumType.STRING)
        var permission: Permission = Permission.USER
) {
    constructor(user: User, device: Device, permission: Permission) : this(
            userDeviceId = UserDeviceId(
                    userId = user.id,
                    deviceId = device.id
            ),
            user = user,
            device = device,
            permission = permission
    )

    @Embeddable
    data class UserDeviceId(
            val userId: Int?,
            val deviceId: Int?
    ) : Serializable
}
