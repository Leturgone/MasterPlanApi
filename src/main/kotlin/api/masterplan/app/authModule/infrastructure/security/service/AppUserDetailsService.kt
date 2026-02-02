package api.masterplan.app.authModule.infrastructure.security.service

import api.masterplan.app.authModule.domain.`interface`.UserRepository
import api.masterplan.app.authModule.domain.model.value.UserLogin
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanAuthException
import api.masterplan.app.authModule.infrastructure.security.principal.AppUserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService(private val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? {
        val login = UserLogin.create(username ?: throw MasterPlanAuthException.EmptyLogin())
        val userEntity = userRepository.findByLogin(login) ?: throw MasterPlanAuthException
            .UserNotExistsWithLogin(login)
        return AppUserPrincipal(userEntity)
    }
}