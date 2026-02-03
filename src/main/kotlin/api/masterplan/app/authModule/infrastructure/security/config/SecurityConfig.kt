package api.masterplan.app.authModule.infrastructure.security.config

import api.masterplan.app.authModule.infrastructure.security.filter.JwtFilter
import api.masterplan.app.authModule.infrastructure.security.service.AppUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class SecurityConfig(
    private val appUserDetailsService: AppUserDetailsService,
    private val jwtFilter: JwtFilter,
) {

    @Bean
    fun  passwordEncoder(): PasswordEncoder {
        val iterations = 2
        val memoryKb = 32768
        val parallelism = 1
        val hashLength = 32
        val saltLength = 16

        val encoder = Argon2PasswordEncoder(
            saltLength,
            hashLength,
            parallelism,
            memoryKb,
            iterations
        )
        return encoder
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain{

        http.csrf { it.disable() }

        http.authorizeHttpRequests { auth ->
            auth.requestMatchers("/swagger-ui/**", "/api/v1/api-docs/**","/api/v1/api-docs.yaml").permitAll()
            auth.requestMatchers("/api/v1/auth/login").permitAll()
            auth.requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
            auth.anyRequest().authenticated()
        }
        // Загрузка пользователя при аутентификации
        http.userDetailsService(appUserDetailsService)

        http.sessionManagement{it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}

        // Вставка фильтра перед первым фильтром
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)


        return http.build()
    }
}