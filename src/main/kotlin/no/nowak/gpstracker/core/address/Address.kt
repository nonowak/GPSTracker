package no.nowak.gpstracker.core.address

import java.io.Serializable
import javax.persistence.*

@Entity
data class Address(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @OneToOne
        val country: Country
) : Serializable

@Entity
data class Country(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val name: String
) : Serializable