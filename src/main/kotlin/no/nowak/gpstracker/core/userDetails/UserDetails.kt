package no.nowak.gpstracker.core.userDetails

import com.fasterxml.jackson.annotation.JsonIgnore
import no.nowak.gpstracker.core.address.Address
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
data class UserDetails(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        var firstName: String = "",
        var lastName: String = "",

        @OneToOne
        var address: Address? = null,

        @JsonIgnore
        var activationKey: String = UUID.randomUUID().toString(),
        @JsonIgnore
        var resetPasswordKey: String? = ""
) : Serializable
