package api.masterplan.app.authModule.infrastructure.security.service

import api.masterplan.app.authModule.application.ports.UserCredentialsProvider
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.infrastructure.security.principal.AppUserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(private val userCredentialsProvider: UserCredentialsProvider): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        val login = AuthUserLogin.validate(username)

        val userEntity = userCredentialsProvider.getUserDetailsByUsername(login)

        return AppUserPrincipal(userEntity)
    }
}