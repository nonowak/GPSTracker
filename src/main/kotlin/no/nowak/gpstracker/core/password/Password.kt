package no.nowak.gpstracker.core.password

import java.time.LocalDateTime
import javax.persistence.Entity

@Entity
data class Password(
        val currentHash: String,
        val previousHash: String?,
        val modifiedOn: LocalDateTime
)