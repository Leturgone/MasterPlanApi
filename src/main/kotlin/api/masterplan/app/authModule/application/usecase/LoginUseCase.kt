package api.masterplan.app.authModule.application.usecase

import api.masterplan.app.authModule.application.command.LoginCommand
import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.application.service.AuthService
import api.masterplan.app.authModule.application.service.TokenGeneratorService

class LoginUseCase(
    private val authService: AuthService,
    private val tokenGeneratorService: TokenGeneratorService
) {
    operator fun invoke(command: LoginCommand): Result<JwtToken>{
        return authService.authenticate(command.login,command.password).fold(
            onSuccess = { userDto ->
                tokenGeneratorService.generateToken(userDto.userId,userDto.roles).fold(
                    onSuccess = { Result.success(it)},
                    onFailure = { Result.failure(it)}
                )
                        },
            onFailure = { Result.failure(it)}
        )
    }

}