package no.nowak.core.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import java.io.IOException
import java.util.*

object Tools {
    fun generateUUIDString(): String = UUID.randomUUID().toString()

    @Throws(IOException::class)
    inline fun <reified T> convertJsonToObject(json: String): T {
        val mapper = ObjectMapper()
        mapper.findAndRegisterModules()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        mapper.dateFormat = ISO8601DateFormat()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return mapper.readValue(json, object : TypeReference<T>() {})
    }
}