package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.device.Device
import no.nowak.gpstracker.core.user.userDetails.Role
import javax.persistence.*

@Entity
data class UserDevice(

        @EmbeddedId
        val userDeviceId: UserDeviceId,

        @Id
        @ManyToOne
        @JoinColumn(name = "userId")
        val user: User,

        @Id
        @ManyToOne
        @JoinColumn(name = "deviceId")
        val device: Device,

        @Enumerated(EnumType.STRING)
        val role: Role = Role.USER
) {
    @Embeddable
    data class UserDeviceId(
            val userId: Long,
            val deviceId: Long

    )
}