package no.nowak.core.infrastructure.security

import no.nowak.core.infrastructure.Paths
import no.nowak.core.user.UserApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.util.FileCopyUtils
import java.io.IOException
import javax.servlet.http.HttpServletResponse

@Profile("!test")
@Configuration
@EnableResourceServer
class ResourceServer : ResourceServerConfigurerAdapter() {

    @Autowired
    lateinit var tokenStore: TokenStore

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint { _, response, _ -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "${Paths.USER_PATH}${UserApi.REGISTER_PATH}/**",
                        "${Paths.PASSWORD_PATH}/**",
                        "${Paths.GPS_PATH}${Paths.MEASUREMENTS_PATH}/**"
                ).permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic().disable()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        resources!!
                .tokenStore(tokenStore)
                .resourceId("gpsTracker")
    }
}