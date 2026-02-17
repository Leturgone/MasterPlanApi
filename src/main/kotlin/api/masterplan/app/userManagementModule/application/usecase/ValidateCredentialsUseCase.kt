package api.masterplan.app.userManagementModule.application.usecase

import api.masterplan.app.userManagementModule.application.command.ValidateCredentialsCommand
import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.interfaces.PasswordHasherService
import api.masterplan.app.userManagementModule.domain.interfaces.UserService
import org.springframework.stereotype.Service

@Service
class ValidateCredentialsUseCase(
    private val userService: UserService,
    private val passwordHasherService: PasswordHasherService
) {
    operator fun invoke(command: ValidateCredentialsCommand): Result<AppUserDetails> {
        return try {
            val user = userService.getUserByLogin(login = command.login)

            if (!passwordHasherService.verify(
                rawPassword = command.password,
                hash = user.password
            )) throw UserManagementException.InvalidUserCredentialsException()

            return Result.success(user)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}