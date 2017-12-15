package no.nowak.core.device

import no.nowak.core.user.UserDevice
import java.io.Serializable
import javax.persistence.*

@Entity
class Device(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @OneToMany(mappedBy = "device")
        val user: List<UserDevice> = emptyList(),

        @Enumerated(EnumType.STRING)
        val type: Type
) : Serializable

enum class Type {
    GPSTRACKER;
}

