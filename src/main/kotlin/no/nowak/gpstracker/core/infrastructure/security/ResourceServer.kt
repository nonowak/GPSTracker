package no.nowak.gpstracker.core.infrastructure.security

import no.nowak.gpstracker.core.infrastructure.Paths
import no.nowak.gpstracker.core.user.UserApi
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import javax.servlet.http.HttpServletResponse

@Profile("!test")
@Configuration
@EnableResourceServer
class ResourceServer : ResourceServerConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint { _, response, _ -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers("${Paths.USER_PATH}${UserApi.REGISTER_PATH}/**").permitAll()
                .antMatchers("${Paths.USER_PATH}${UserApi.REGISTER_PATH}/**").permitAll()
                .antMatchers("/**").hasRole("USER")
                .and()
                .httpBasic().disable()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources!!.resourceId("gpsTracker")
    }
}