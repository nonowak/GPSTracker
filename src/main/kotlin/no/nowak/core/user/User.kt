package no.nowak.core.user

import com.fasterxml.jackson.annotation.JsonIgnore
import no.nowak.core.device.Device
import no.nowak.core.device.Permission
import no.nowak.core.infrastructure.Tools
import no.nowak.core.password.Password
import no.nowak.core.user.Role.USER
import no.nowak.core.userDetails.UserInfo
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "[user]")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        var emailAddress: String,
        @OneToOne(cascade = [(CascadeType.ALL)])
        val password: Password,
        @OneToOne(cascade = [(CascadeType.ALL)])
        val userInfo: UserInfo,
        var enabled: Boolean = false,
        @JsonIgnore
        var activationKey: String = Tools.generateUUIDString(),
        @OneToMany(mappedBy = "user")
        val devices: MutableList<UserDevice> = mutableListOf(),

        @Enumerated(EnumType.STRING)
        val role: Role = USER
) : Serializable {

    fun addDevice(device: Device, permission: Permission) {
        var userDevice = UserDevice(
                user = this,
                device = device,
                permission = permission
        )
        devices.add(userDevice)
        device.users.add(userDevice)
    }

    fun removeDevice(device: Device) {
        devices.filter { it.device == device }.map {
            devices.remove(it)
            it.device.users.remove(it)
        }
    }

}

enum class Role {
    USER,
    ADMIN
}
