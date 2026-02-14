package api.masterplan.app.authModule.infrastructure.security.service

import api.masterplan.app.authModule.domain.dto.JwtToken
import api.masterplan.app.authModule.domain.exception.AuthException
import api.masterplan.app.authModule.domain.interfaces.TokenGeneratorService
import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.AuthUserRole
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TokenGeneratorServiceImpl(private val jwtService: JwtService): TokenGeneratorService {

    private val  logger = LoggerFactory.getLogger(this::class.java)

    override fun generateToken(authUserId: AuthUserId, authUserRoles: Set<AuthUserRole>): JwtToken {
        return try {

            val token = jwtService.generateToken(
                userId = authUserId.value.toString(),
                roles = authUserRoles.map { it.name }
            )

            logger.info("Generated token for user ${authUserId.value}")
            JwtToken(
                token = token,
                authUserId = authUserId,
                roles = authUserRoles
            )
        }catch (e: Exception){
            logger.warn("Token generation failed for user ${authUserId.value} ", e)
            throw AuthException.TokenGenerationException(authUserId,e.message)
        }

    }

}