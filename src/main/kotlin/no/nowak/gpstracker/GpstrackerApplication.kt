package no.nowak.gpstracker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer

@SpringBootApplication
class GpstrackerApplication: SpringBootServletInitializer() {

    fun main(args: Array<String>) {
        SpringApplication.run(GpstrackerApplication::class.java, *args)
    }

    override fun configure(application: SpringApplicationBuilder) : SpringApplicationBuilder {
        return application.sources(GpstrackerApplication::class.java)
    }
}
