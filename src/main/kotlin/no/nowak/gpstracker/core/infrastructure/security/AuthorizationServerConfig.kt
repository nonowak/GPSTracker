package no.nowak.gpstracker.core.infrastructure.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource


//@Profile("!test")
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var dataSource: DataSource

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients!!.inMemory()
                .withClient("gpsTracker")
                .scopes("read", "write")
                .authorizedGrantTypes("refresh_token", "password")
                .authorities("ROLE_USER", "ROLE_ADMIN")
                .accessTokenValiditySeconds(300)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security!!.allowFormAuthenticationForClients()
                .passwordEncoder(encoder())
    }

    @Bean
    fun tokenStore() = JdbcTokenStore(dataSource)

    @Bean
    fun encoder(): PasswordEncoder =
            BCryptPasswordEncoder()
}