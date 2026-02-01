package api.masterplan.app.authModule.infrastructure.security.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class SecurityConfig {

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
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/swagger-ui/**", "/api/v1/api-docs/**","/api/v1/api-docs.yaml"
                    ).permitAll()
                    .requestMatchers("/api/v1/auth/login").permitAll()
                    .anyRequest().authenticated()
            }
            .build()
    }
}