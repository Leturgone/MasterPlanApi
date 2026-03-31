package api.masterplan.app.authModule.infrastructure.security.filter

import api.masterplan.app.authModule.infrastructure.security.service.AppUserDetailsService
import api.masterplan.app.authModule.infrastructure.security.service.JwtService
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception
import kotlin.text.startsWith

@Component
class JwtHandshakeInterceptor(
    private val appUserDetailsService: AppUserDetailsService,
    private val jwtService: JwtService
): HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: Map<String, Any>
    ): Boolean {
        val authHeader = request.headers.getFirst("Authorization")
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false
        }

        val token = authHeader.substring("Bearer ".length)
        val username = try {
            jwtService.extractUsername(token)
        }catch (_: kotlin.Exception){
            null
        }

        if ((username != null) && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = appUserDetailsService.loadUserByUsername(username)

            if (jwtService.isValidToken(token, userDetails)) {

                // Объект, который содержит информацию о пользователе и его роли
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails, null,userDetails.authorities
                )

                // Установка созданного токена в текущий контекст безопасности
                // Аутентификация пользователя
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        return true
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
    }
}