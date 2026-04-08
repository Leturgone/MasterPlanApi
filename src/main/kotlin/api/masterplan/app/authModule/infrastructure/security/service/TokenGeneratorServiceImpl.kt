package api.masterplan.app.authModule.infrastructure.security.service

import api.masterplan.app.authModule.domain.dto.JwtToken
import api.masterplan.app.authModule.domain.exception.AuthException
import api.masterplan.app.authModule.domain.interfaces.TokenGeneratorService
import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserRole
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TokenGeneratorServiceImpl(private val jwtService: JwtService): TokenGeneratorService {

    private val  logger = LoggerFactory.getLogger(this::class.java)

    override fun generateToken(authUserName: AuthUserLogin, authUserRoles: Set<AuthUserRole>,authUserId: AuthUserId): JwtToken {
        return try {

            val token = jwtService.generateToken(
                userName = authUserName.value,
                roles = authUserRoles.map { it.name }
            )

            logger.info("Generated token for user ${authUserName.value}")
            JwtToken(
                token = token,
                authUserName = authUserName,
                roles = authUserRoles,
                authUserId = authUserId
            )
        }catch (e: Exception){
            logger.warn("Token generation failed for user ${authUserName.value} ", e)
            throw AuthException.TokenGenerationException(authUserName,e.message)
        }

    }

}