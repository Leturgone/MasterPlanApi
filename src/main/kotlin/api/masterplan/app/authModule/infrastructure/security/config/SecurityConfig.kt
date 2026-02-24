package api.masterplan.app.authModule.infrastructure.security.config

import api.masterplan.app.authModule.infrastructure.security.filter.JwtFilter
import api.masterplan.app.authModule.infrastructure.security.service.AppUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


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
            auth.requestMatchers("/swagger-ui/**", "/api/v1/api-docs/**","/api/v1/api-docs.yaml").permitAll()
            auth.requestMatchers("/api/v1/auth/login").permitAll()
            auth.requestMatchers("/api/v1/users/admin/**").hasAuthority("ADMIN")
            auth.requestMatchers("/api/v1/employees/admin/**").hasAuthority("ADMIN")
            auth.requestMatchers("/api/v1/employees/dir/**").hasAuthority("DIRECTOR")
            auth.requestMatchers("/api/v1/employees/emp/**").hasAuthority("EMPLOYEE")
            auth.anyRequest().authenticated()
        }
        // СДЕЛАТЬ ФИЛЬТР ДЛЯ АДМИНА  И ДИРЕКТОРА В ЕМПЛОЙ
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