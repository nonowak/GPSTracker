package no.nowak.core.infrastructure.converters

import java.sql.Time
import java.time.LocalTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LocalTimeAttributeConverter : AttributeConverter<LocalTime, Time> {
    override fun convertToDatabaseColumn(localTime: LocalTime?): Time? {
        return if (localTime == null) null else Time.valueOf(localTime)
    }

    override fun convertToEntityAttribute(dbData: Time?): LocalTime? {
        return dbData?.toLocalTime()
    }
}