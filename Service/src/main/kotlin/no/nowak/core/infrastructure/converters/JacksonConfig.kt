package no.nowak.core.infrastructure.converters

import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Configuration
class JacksonConfig : WebMvcConfigurerAdapter() {
    override fun extendMessageConverters(converters: List<HttpMessageConverter<*>>?) {
        for (converter in converters!!) {
            if (converter is MappingJackson2HttpMessageConverter) {
                val objectMapper = converter.objectMapper
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                break
            }
        }
    }
}