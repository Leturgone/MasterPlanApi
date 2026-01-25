package api.masterplan.app.authModule.infrastructure.servicie

import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.application.service.TokenGeneratorService
import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserRole
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanTokenException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey

class TokenGeneratorServiceImpl(
    private val jwtSecret: String,
    private val expirationHours: Long = 12
): TokenGeneratorService {

    override fun generateToken(userId: UserId, userRoles: Set<UserRole>): Result<JwtToken> {

        return try {
            val key: SecretKey by lazy {
                Keys.hmacShaKeyFor(jwtSecret.toByteArray())
            }

            val expirationTime = calculateExpiration()
            val token = Jwts.builder()
                .subject(userId.value.toString())
                .issuer("MasterPlanApi")
                .expiration(expirationTime)
                .id(UUID.randomUUID().toString())
                .claim("roles",userRoles.map { it.name })
                .signWith(key)
                .compact()

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

    private fun calculateExpiration(): Date {
        return Date(System.currentTimeMillis() + expirationHours * 60 * 60 * 1000)
    }

}