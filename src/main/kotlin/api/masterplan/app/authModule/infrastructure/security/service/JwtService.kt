package api.masterplan.app.authModule.infrastructure.security.service

import api.masterplan.app.authModule.domain.exception.AuthException
import api.masterplan.app.authModule.infrastructure.security.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    private val jwtProperties: JwtProperties
) {

    private val key: SecretKey by lazy {
        val decodedBytes = Decoders.BASE64.decode(jwtProperties.secret)

        require(decodedBytes.size >= 32) {
            "JWT secret must be at least 32 bytes for HS256"
        }

        Keys.hmacShaKeyFor(decodedBytes)
    }

    fun generateToken(userName: String, roles:  List<String>): String  {

        val expirationTime = calculateExpiration()
        val token = Jwts.builder()
            .subject(userName)
            .issuer("MasterPlanApi")
            .expiration(expirationTime)
            .id(UUID.randomUUID().toString())
            .claim("roles",roles)
            .signWith(key).compact()

        return token

    }

    fun isValidToken(token: String, user: UserDetails): Boolean {
        val username = extractUsername(token)
        val equalsUsernames = username.equals(user.username)
        val tokenNotExpired = !isTokenExpired(token)
        return equalsUsernames && tokenNotExpired
    }

    private fun calculateExpiration(): Date {
        return Date(System.currentTimeMillis() + jwtProperties.expiration * 60 * 60 * 1000)
    }


    fun isTokenExpired(token: String): Boolean{
        val result = extractExpiration(token)?.before(Date())
            ?: throw AuthException.InvalidToken()
        return result
    }

    private fun extractExpiration(token: String): Date? {
        return extractClaim(token){claims -> claims.expiration}
    }

    fun extractUsername(token: String): String? {
        return extractClaim(token){claims -> claims.subject}
    }


    fun extractRoles(token: String): List<String>? {
        return extractClaim(token){claims -> claims["roles"] as List<String>}
    }


    private fun <T> extractClaim(token: String, resolver: (Claims) -> T): T? {
        val claims = extractAllClaims(token)
        return resolver(claims)
    }

    // Получение данных из токена
    private fun extractAllClaims(token: String): Claims {
        val parser = Jwts.parser()

        parser.verifyWith(key)

        return parser.build().parseSignedClaims(token).getPayload()
    }



}