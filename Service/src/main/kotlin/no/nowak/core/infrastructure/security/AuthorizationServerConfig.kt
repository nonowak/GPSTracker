package no.nowak.core.infrastructure.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import java.util.*


//@Profile("!test")
@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients!!.inMemory()
                .withClient("gpsTracker")
                .scopes("read", "write")
                .autoApprove(true)
                .authorities("ROLE_CLIENT")
                .authorizedGrantTypes("refresh_token", "password")
                .accessTokenValiditySeconds(300)
                .secret("secret")
    }

    @Bean
    fun tokenStore(): TokenStore = JwtTokenStore(tokenEnhancer())

    @Bean
    fun tokenEnhancer(): JwtAccessTokenConverter {
        val keyStoreKeyFactor = KeyStoreKeyFactory(ClassPathResource("jwtOD.jks"), "aqSWdeFR".toCharArray())
        val jwtAccessTokenConverter = CustomTokenEnhancer();
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactor.getKeyPair("jwtOD"))
        return jwtAccessTokenConverter
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .authenticationManager(authenticationManager)
    }

    class CustomTokenEnhancer : JwtAccessTokenConverter() {
        override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {

            val activeUser = (authentication.principal as CustomUserDetails)
            val additionalInfo = LinkedHashMap(accessToken.additionalInformation)

            additionalInfo.put("id", activeUser.user.id)
            additionalInfo.put("email", activeUser.user.emailAddress)
            additionalInfo.put("firstName", activeUser.user.userInfo.firstName)
            additionalInfo.put("lastName", activeUser.user.userInfo.lastName)
            additionalInfo.put("authorities", activeUser.authorities.toList())

            val customAccessToken = DefaultOAuth2AccessToken(accessToken)
            customAccessToken.additionalInformation = additionalInfo
            return super.enhance(customAccessToken, authentication)
        }
    }
}