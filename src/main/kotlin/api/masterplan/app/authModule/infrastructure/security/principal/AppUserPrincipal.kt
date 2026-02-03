package api.masterplan.app.authModule.infrastructure.security.principal

import api.masterplan.app.authModule.domain.model.entity.AppUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AppUserPrincipal(private val userEntity: AppUser) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return userEntity.roles.map {
            SimpleGrantedAuthority(it.name)
        }
    }

    override fun getPassword(): String {
        return userEntity.password.value
    }

    override fun getUsername(): String {
        return userEntity.login.value
    }
}