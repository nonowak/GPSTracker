package no.nowak.core.deviceDictionary

import no.nowak.core.device.Type
import no.nowak.core.infrastructure.converters.LocalDateTimeAttributeConverter
import no.nowak.core.user.User
import org.hibernate.annotations.ColumnTransformer
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