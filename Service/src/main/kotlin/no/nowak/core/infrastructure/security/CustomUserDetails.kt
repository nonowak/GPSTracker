package no.nowak.core.infrastructure.security

import no.nowak.core.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

class CustomUserDetails(val user: User) : UserDetails, Serializable {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority("ROLE_" + user.role.name))

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = user.emailAddress

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = user.password.currentHash

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}