package api.masterplan.app.authModule.infrastructure.security.config

import api.masterplan.app.authModule.application.service.TokenGeneratorService
import api.masterplan.app.authModule.infrastructure.servicie.TokenGeneratorServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {

    @Bean
    fun jwtSecret(): String{
        return System.getenv("JWT_SECRET") ?: throw IllegalArgumentException("JWT_SECRET not set!")
    }

    @Bean
    fun tokenGeneratorService(jwtSecret: String): TokenGeneratorService {
        return TokenGeneratorServiceImpl(jwtSecret)
    }

}