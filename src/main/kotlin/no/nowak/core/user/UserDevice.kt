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
        val user: User,

        @ManyToOne
        @MapsId("deviceId")
        @JoinColumn(name = "deviceId")
        val device: Device,

        @Enumerated(EnumType.STRING)
        val role: Permission = Permission.USER
) {
    @Embeddable
    data class UserDeviceId(
            val userId: Long,
            val deviceId: Long
    ) : Serializable
}
