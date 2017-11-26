package no.nowak.gpstracker.core.address

import java.io.Serializable
import javax.persistence.*

@Entity
data class Address(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        @OneToOne
        val country: Country
) : Serializable

@Entity
data class Country(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,

        val name: String
) : Serializable