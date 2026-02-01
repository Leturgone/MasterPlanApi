package api.masterplan.app.authModule.infrastructure.servicie

import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.application.service.TokenGeneratorService
import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserRole
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanTokenException
import api.masterplan.app.authModule.infrastructure.security.config.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class TokenGeneratorServiceImpl(
    private val jwtProperties: JwtProperties
): TokenGeneratorService {

    override fun generateToken(userId: UserId, userRoles: Set<UserRole>): Result<JwtToken> {

        return try {
            val key: SecretKey by lazy {
                val decodedBytes = Decoders.BASE64.decode(jwtProperties.secret)

                require(decodedBytes.size >= 32) {
                    "JWT secret must be at least 32 bytes for HS256"
                }

                Keys.hmacShaKeyFor(decodedBytes)
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
        return Date(System.currentTimeMillis() + jwtProperties.expiration * 60 * 60 * 1000)
    }

}