package api.masterplan.app.authModule.infrastructure.servicie

import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.application.service.TokenGeneratorService
import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.UserRole
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanTokenException
import api.masterplan.app.authModule.infrastructure.security.service.JwtService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TokenGeneratorServiceImpl(private val jwtService: JwtService): TokenGeneratorService {

    private val  logger = LoggerFactory.getLogger(this::class.java)

    override fun generateToken(authUserId: AuthUserId, userRoles: Set<UserRole>): Result<JwtToken> {
        return try {

            val token = jwtService.generateToken(
                userId = authUserId.value.toString(),
                roles = userRoles.map { it.name }
            )

            logger.info("Generated token for user ${authUserId.value}")
            Result.success(
                JwtToken(
                    token = token,
                    authUserId = authUserId,
                    roles = userRoles)
            )
        }catch (e: Exception){
            logger.warn("Token generation failed for user ${authUserId.value} ", e)
            Result.failure(MasterPlanTokenException.TokenGenerationException(authUserId,e.message))
        }

    }

}