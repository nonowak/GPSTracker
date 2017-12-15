package no.nowak.core

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry

@Configuration
class WebConfig: DelegatingWebMvcConfiguration() {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry){
        super.addResourceHandlers(registry)
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }
}