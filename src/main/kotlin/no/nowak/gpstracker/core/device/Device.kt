package no.nowak.gpstracker.core.device

import no.nowak.gpstracker.core.user.UserDevice
import javax.persistence.*

@Entity
class Device(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @OneToMany(mappedBy = "device")
        val user: List<UserDevice> = emptyList()
)