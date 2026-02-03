package api.masterplan.app.authModule.infrastructure.security.filter

import api.masterplan.app.authModule.infrastructure.security.service.JwtService
import api.masterplan.app.authModule.infrastructure.security.service.AppUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val appUserDetailsService: AppUserDetailsService,
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return;
        }

        val token = authHeader.substring("Bearer ".length)
        val username = jwtService.extractUsername(token)

        // Проверка, что пользователь еще не аутентифицирован в текущем контексте безопасности
        if ((username != null) && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = appUserDetailsService.loadUserByUsername(username)

            if (jwtService.isValidToken(token, userDetails)) {

                // Объект, который содержит информацию о пользователе и его роли
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails, null,userDetails.authorities
                )

                // Добавляем дополнительные детали из текущего запроса к объекту аутентификации
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                // Установка созданного токена в текущий контекст безопасности
                // Аутентификация пользователя
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        // Передача следующему фильтру по цепочке
        filterChain.doFilter(request, response)
    }

}