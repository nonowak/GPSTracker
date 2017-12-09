package no.nowak.gpstracker

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.ISO8601DateFormat
import org.springframework.web.bind.annotation.*
import java.io.IOException
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

object TestUtil {

    fun convertObjectToJson(`object`: Any): String {
        val mapper = ObjectMapper()
        mapper.findAndRegisterModules()
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS)
        mapper.dateFormat = ISO8601DateFormat()
        return mapper.writeValueAsString(`object`)
    }

    @Throws(IOException::class)
    inline fun <reified T> convertJsonToObject(json: String): T {
        val mapper = ObjectMapper()
        mapper.findAndRegisterModules()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        mapper.dateFormat = ISO8601DateFormat()
        return mapper.readValue(json, object : TypeReference<T>() {})
    }

    fun <T> getPathForMethod(kFunction: KFunction<*>, clazz: Class<T>): String {
        val api = clazz.getAnnotation(RequestMapping::class.java).value[0]
        val method = kFunction.findAnnotation<PostMapping>()?.value?.get(0)
                ?: kFunction.findAnnotation<GetMapping>()?.value?.get(0)
                ?: kFunction.findAnnotation<DeleteMapping>()?.value?.get(0)
                ?: kFunction.findAnnotation<PutMapping>()?.value?.get(0)
                ?: kFunction.findAnnotation<PatchMapping>()?.value?.get(0)
        return api + method!!
    }
}