package api.masterplan.app.authModule.application.usecases

import api.masterplan.app.authModule.application.commands.LoginCommand
import api.masterplan.app.authModule.application.dto.JwtToken
import api.masterplan.app.authModule.application.services.AuthService
import api.masterplan.app.authModule.application.services.TokenGeneratorService

class LoginUseCase(
    private val authService: AuthService,
    private val tokenGeneratorService: TokenGeneratorService
) {
    suspend operator fun invoke(command: LoginCommand): Result<JwtToken>{
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