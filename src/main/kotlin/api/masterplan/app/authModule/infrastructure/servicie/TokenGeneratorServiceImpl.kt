package api.masterplan.app.authModule.infrastructure.servicie

import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.application.service.TokenGeneratorService
import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserRole
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanTokenException
import api.masterplan.app.authModule.infrastructure.security.service.JwtService
import org.springframework.stereotype.Service

@Service
class TokenGeneratorServiceImpl(private val jwtService: JwtService): TokenGeneratorService {

    override fun generateToken(userId: UserId, userRoles: Set<UserRole>): Result<JwtToken> {
        return try {

            val token = jwtService.generateToken(
                userId = userId.value.toString(),
                roles = userRoles.map { it.name }
            )

            Result.success(
                JwtToken(
                    token = token,
                    userId = userId,
                    roles = userRoles)
            )
        }catch (e: Exception){
            Result.failure(MasterPlanTokenException.TokenGenerationException(userId,e.message))
        }

    }

}