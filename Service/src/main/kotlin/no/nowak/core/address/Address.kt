package no.nowak.core.address

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Address(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val postalCode: String? = "",
        val countryName: String? = "",
        val cityName: String? = "",
        val streetName: String? = ""
) : Serializable
