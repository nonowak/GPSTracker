package no.nowak.gpstracker.core.infrastructure.converters

import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter(autoApply = true)
class LocalDateTimeAttributeConverter : AttributeConverter<LocalDateTime, Timestamp> {

    override fun convertToDatabaseColumn(locDateTime: LocalDateTime?): Timestamp? {
        return if (locDateTime == null) null else Timestamp.valueOf(locDateTime.withNano(0))
    }

    override fun convertToEntityAttribute(sqlTimestamp: Timestamp?): LocalDateTime? {
        return sqlTimestamp?.toLocalDateTime()?.withNano(0)
    }
}