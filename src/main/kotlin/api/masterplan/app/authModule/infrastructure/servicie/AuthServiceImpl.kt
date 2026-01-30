package api.masterplan.app.authModule.infrastructure.servicie

import api.masterplan.app.authModule.application.dto.UserCredentials
import api.masterplan.app.authModule.application.service.AuthService
import api.masterplan.app.authModule.domain.`interface`.PasswordHasher
import api.masterplan.app.authModule.domain.`interface`.UserRepository
import api.masterplan.app.authModule.domain.model.value.UserLogin
import api.masterplan.app.authModule.domain.model.value.UserPassword
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanAuthException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher
): AuthService {

    private val  logger = LoggerFactory.getLogger(this::class.java)

    override fun authenticate(
        login: UserLogin,
        password: UserPassword
    ): Result<UserCredentials> {
        return try {

            val user = userRepository.findByLogin(login) ?: throw MasterPlanAuthException.UserNotExistsWithLogin(login)

            if (passwordHasher.verify(password, user.password)) {
                return Result.failure(MasterPlanAuthException.InvalidCredentials())
            }

            logger.info("Authentication success for login=${login.value}")

            Result.success(UserCredentials(user.id, user.roles))

        }catch (e: Exception){
            logger.warn("Authentication failed for login=${login.value}", e)

            Result.failure(e)
        }
    }

}