package no.nowak.gpstracker.core.address

import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
data class Address(
        @OneToOne
        val country: Country
)

@Entity
data class Country(
        val name: String
)