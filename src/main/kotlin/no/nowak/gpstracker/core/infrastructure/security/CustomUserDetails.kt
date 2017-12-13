package no.nowak.gpstracker.core.infrastructure.security

import no.nowak.gpstracker.core.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(private val user: User) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority(user.role.name))

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = user.emailAddress

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = user.password.currentHash

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}