package no.nowak

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class GpstrackerApplication{
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    @Throws(Exception::class)
    fun authenticationManager(builder: AuthenticationManagerBuilder) {
        builder.userDetailsService(userDetailsService).passwordEncoder(encoder())
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(GpstrackerApplication::class.java, *args)
}