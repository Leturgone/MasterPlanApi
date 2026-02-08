package api.masterplan.app.authModule.application.service

import api.masterplan.app.authModule.domain.dto.UserAuthCredentials
import api.masterplan.app.authModule.application.ports.UserCredentialsProvider
import api.masterplan.app.authModule.domain.interfaces.AuthService
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userCredentialsProvider: UserCredentialsProvider,
): AuthService {

    private val  logger = LoggerFactory.getLogger(this::class.java)

    override fun authenticate(
        login: AuthUserLogin,
        password: AuthUserPassword
    ): Result<UserAuthCredentials> {
        return try {
            val userAuthCredentials = userCredentialsProvider.validateCredentials(login,password)
            logger.info("Authentication success for login=${login.value}")

            Result.success(userAuthCredentials)

        }catch (e: Exception){
            logger.warn("Authentication failed for login=${login.value}", e)
            Result.failure(e)
        }
    }

}