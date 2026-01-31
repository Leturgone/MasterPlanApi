package api.masterplan.app.authModule.infrastructure.security.filter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityFilter {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain{
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/swagger-ui/**", "/api/v1/api-docs/**","/api/v1/api-docs.yaml"
                    ).permitAll()
                    .requestMatchers("/api/v1/auth").permitAll()
                    .anyRequest().authenticated()
            }
            .build()
    }
}