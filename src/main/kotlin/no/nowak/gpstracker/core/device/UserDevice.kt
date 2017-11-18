package no.nowak.gpstracker.core.device

import javax.persistence.*

@Entity
data class UserDevice(

        @EmbeddedId
        val userDeviceId: UserDeviceId,

        @ManyToOne
        @MapsId("userId")
        val userId: Long,

        @ManyToOne
        @MapsId("deviceId")
        val deviceId: Long,

        val permission: Permission
)


@Embeddable
data class UserDeviceId(
        val userId: Long,

        val deviceId: Long
)