package no.nowak.gpstracker.core.user.userDetails

import no.nowak.gpstracker.core.address.Address
import javax.persistence.*

@Entity
data class UserDetails(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val firstName: String = "",
        val lastName: String = "",

        @OneToOne
        val address: Address?
)
