package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.password.Password
import no.nowak.gpstracker.core.user.userDetails.Role
import no.nowak.gpstracker.core.user.userDetails.UserDetails
import javax.persistence.*

@Entity(name = "[user]")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val emailAddress: String,
        @OneToOne
        val password: Password,
        @OneToOne
        val userDetails: UserDetails?,
        val role: Role = Role.USER,

        @OneToMany(mappedBy = "user")
        val devices: List<UserDevice> = emptyList()
)
