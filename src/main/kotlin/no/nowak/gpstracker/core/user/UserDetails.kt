package no.nowak.gpstracker.core.user

import no.nowak.gpstracker.core.address.Address

@Entity
data class UserDetails(
        val firstName: String = "",
        val lastName: String = "",
        @OneToOne
        val address: Address?
)
