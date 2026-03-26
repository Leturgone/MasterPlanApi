package api.masterplan.app.authModule.application.service

import api.masterplan.app.authModule.application.ports.UserCredentialsProvider
import api.masterplan.app.authModule.domain.dto.UserAuthCredentials
import api.masterplan.app.authModule.domain.interfaces.AuthService
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword
import api.masterplan.app.logging.annotations.LoggingMethod
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userCredentialsProvider: UserCredentialsProvider,
): AuthService {


    @LoggingMethod(moduleName = "authModule")
    override fun authenticate(
        login: AuthUserLogin,
        password: AuthUserPassword
    ): UserAuthCredentials {
        val userAuthCredentials = userCredentialsProvider.validateCredentials(login,password)
        return userAuthCredentials
    }

}