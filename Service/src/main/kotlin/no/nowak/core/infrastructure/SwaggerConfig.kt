package no.nowak.core.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun CoreApi(): Docket {

        return Docket(DocumentationType.SWAGGER_2)
                .groupName("Core")
                .select()
                .apis(RequestHandlerSelectors.basePackage("no.nowak.core"))
                .build()
                .apiInfo(ApiInfo(
                        "Core",
                        "Core",
                        "1.0",
                        "",
                        Contact("Norbert Nowak", "", "nonowak@outlook.com"),
                        "",
                        "",
                        listOf()
                ))
    }

    @Bean
    fun GpsTrackerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("GPSTracker")
                .select()
                .apis(RequestHandlerSelectors.basePackage("no.nowak.gpstracker"))
                .build()
                .apiInfo(ApiInfo(
                        "GPSTracker",
                        "GPSTracker",
                        "1.0",
                        "",
                        Contact("Norbert Nowak", "", "nonowak@outlook.com"),
                        "",
                        "",
                        listOf()
                ))
    }
}