package no.nowak.gpstracker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class GpstrackerApplication

fun main(args: Array<String>) {
    SpringApplication.run(GpstrackerApplication::class.java, *args)
}
