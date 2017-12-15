package no.nowak.gpstracker.core.deviceDictionary

import no.nowak.gpstracker.core.device.Type
import no.nowak.gpstracker.core.infrastructure.Tools
import no.nowak.gpstracker.core.infrastructure.converters.LocalDateTimeAttributeConverter
import no.nowak.gpstracker.core.user.User
import org.hibernate.annotations.ColumnTransformer
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class DeviceDictionary(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Convert(converter = LocalDateTimeAttributeConverter::class)
        val createdOn: LocalDateTime = LocalDateTime.now().withNano(0),

        @OneToOne(cascade = [])
        val createdBy: User,
        val enabled: Boolean = false,

        @Column(unique = true)
        @ColumnTransformer(read = "pgp_sym_decrypt(token::bytea, 'gpsTracker123')", write = "pgp_sym_encrypt(?, 'gpsTracker123')")
        val token: String = "",

        @Enumerated(EnumType.STRING)
        val deviceType: Type
)