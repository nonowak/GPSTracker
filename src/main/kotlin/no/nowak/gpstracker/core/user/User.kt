package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.password.Password
import no.nowak.gpstracker.core.userDetails.UserDetails
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "[user]")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        val emailAddress: String,
        @OneToOne
        val password: Password,
        @OneToOne
        val userDetails: UserDetails,
        val enabled: Boolean = false,

        @OneToMany(mappedBy = "user")
        val devices: List<UserDevice> = emptyList()
) : Serializable
