package no.nowak.gpstracker.core.user

import com.fasterxml.jackson.annotation.JsonIgnore
import no.nowak.gpstracker.core.infrastructure.Tools
import no.nowak.gpstracker.core.password.Password
import no.nowak.gpstracker.core.userDetails.UserInfo
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
        val devices: List<UserDevice> = emptyList()
) : Serializable
