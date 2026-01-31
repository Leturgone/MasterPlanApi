package api.masterplan.app.authModule.infrastructure.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
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

}