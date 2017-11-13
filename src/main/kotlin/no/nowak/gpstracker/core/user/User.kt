package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.password.Password
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
data class User(
        val name: String?,
        val emailAddress: String,
        @OneToOne
        val password: Password,
        @OneToOne
        val userDetails: UserDetails?
)
