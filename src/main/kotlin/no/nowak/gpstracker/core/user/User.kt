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
        var emailAddress: String,
        @OneToOne(cascade = [(CascadeType.ALL)])
        val password: Password,
        @OneToOne(cascade = [(CascadeType.ALL)])
        val userDetails: UserDetails,
        var enabled: Boolean = false,

        @OneToMany(mappedBy = "user")
        val devices: List<UserDevice> = emptyList()
) : Serializable
