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
        val id: Int? = null,
        val emailAddress: String,
        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        val password: Password,
        @OneToOne(cascade = arrayOf(CascadeType.ALL))
        val userDetails: UserDetails,
        val enabled: Boolean = false,

        @OneToMany(mappedBy = "user")
        val devices: List<UserDevice> = emptyList()
) : Serializable
