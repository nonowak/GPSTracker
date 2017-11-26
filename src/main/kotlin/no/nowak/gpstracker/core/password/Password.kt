package no.nowak.gpstracker.core.password

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Password(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val currentHash: String,

        val previousHash: String?,

        val modifiedOn: LocalDateTime?
)