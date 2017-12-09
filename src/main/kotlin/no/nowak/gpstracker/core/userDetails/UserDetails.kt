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
        val id: Int,

        val firstName: String = "",
        val lastName: String = "",

        @OneToOne
        val address: Address?,

        @JsonIgnore
        val activationKey: String = UUID.randomUUID().toString(),
        @JsonIgnore
        val resetPasswordKey: String?
) : Serializable
