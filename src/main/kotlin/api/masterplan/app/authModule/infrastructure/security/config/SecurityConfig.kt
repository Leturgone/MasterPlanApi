package api.masterplan.app.authModule.infrastructure.security.config

import api.masterplan.app.authModule.infrastructure.security.filter.JwtFilter
import api.masterplan.app.authModule.infrastructure.security.service.AppUserDetailsService
import io.jsonwebtoken.security.Message
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class SecurityConfig(
    private val appUserDetailsService: AppUserDetailsService,
    private val jwtFilter: JwtFilter,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain{

        http.csrf { it.disable() }

        http.authorizeHttpRequests { auth ->
            auth.requestMatchers(
                "/swagger-ui/**","/error",
                "/api/v1/api-docs/**",
                "/api/v1/api-docs.yaml",
                "/websocket/**"
            ).permitAll()
            auth.requestMatchers("/api/v1/auth/login").permitAll()
            auth.requestMatchers("/api/v1/users/admin/**").hasAuthority("ADMIN")
            auth.requestMatchers("/api/v1/employees/admin/**").hasAuthority("ADMIN")
            auth.requestMatchers("/api/v1/employees/dir/**").hasAuthority("DIRECTOR")
            auth.requestMatchers("/api/v1/employees/emp/**").hasAuthority("EMPLOYEE")
            auth.requestMatchers("/api/v1/files/emp/**").hasAuthority("EMPLOYEE")
            auth.requestMatchers("/api/v1/plans/dir/**").hasAuthority("DIRECTOR")
            auth.requestMatchers("/api/v1/plans/emp/**").hasAuthority("EMPLOYEE")
            auth.requestMatchers("/api/v1/tasks/dir/**").hasAuthority("DIRECTOR")
            auth.requestMatchers("/api/v1/tasks/emp/**").hasAuthority("EMPLOYEE")
            auth.requestMatchers("/api/v1/reports/dir/**").hasAuthority("DIRECTOR")
            auth.requestMatchers("/api/v1/reports/emp/**").hasAuthority("EMPLOYEE")
            auth.requestMatchers("/api/v1/requests/dir/").hasAnyRole("ADMIN", "DIRECTOR")
            auth.requestMatchers("/api/v1/requests/admin/").hasRole("ADMIN")
            auth.anyRequest().authenticated()
        }

        // Загрузка пользователя при аутентификации
        http.userDetailsService(appUserDetailsService)

        http.sessionManagement{it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}

        // Вставка фильтра перед первым фильтром
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.httpBasic { it.disable() }
        http.formLogin { it.disable() }
        http.logout { it.disable() }

        return http.build()


    }
}