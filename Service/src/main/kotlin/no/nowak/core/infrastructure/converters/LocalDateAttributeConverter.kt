package no.nowak.core.infrastructure.converters

import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter(autoApply = true)
class LocalDateAttributeConverter : AttributeConverter<LocalDate, Date> {

    override fun convertToDatabaseColumn(locDate: LocalDate?): Date? {
        return if (locDate == null) null else Date.valueOf(locDate)
    }

    override fun convertToEntityAttribute(sqlDate: Date?): LocalDate? {
        return sqlDate?.toLocalDate()
    }
}