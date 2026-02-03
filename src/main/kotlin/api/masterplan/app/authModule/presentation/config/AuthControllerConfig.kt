package api.masterplan.app.authModule.presentation.config

import api.masterplan.app.authModule.application.service.AuthService
import api.masterplan.app.authModule.application.service.TokenGeneratorService
import api.masterplan.app.authModule.application.usecase.LoginUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthControllerConfig {

    @Bean
    fun loginUseCase(authService: AuthService, tokenGeneratorService: TokenGeneratorService): LoginUseCase {
        return LoginUseCase(authService, tokenGeneratorService)
    }
}