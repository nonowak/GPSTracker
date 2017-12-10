package no.nowak.gpstracker.core.password

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Password(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val currentHash: String,

        val previousHash: String? = "",

        val modifiedOn: LocalDateTime? = null
) : Serializable