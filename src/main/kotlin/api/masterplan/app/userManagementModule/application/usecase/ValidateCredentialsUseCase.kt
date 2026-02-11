package api.masterplan.app.userManagementModule.application.usecase

import api.masterplan.app.userManagementModule.application.command.ValidateUserCommand
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
    operator fun invoke(command: ValidateUserCommand): Result<AppUserDetails> {
        return userService.getUserByLogin(login = command.login).fold(
            onSuccess = { user ->
                if (!passwordHasherService.verify(command.password,user.password)){
                    Result.failure(UserManagementException.InvalidUserCredentialsException())
                }else{
                    Result.success(user)
                }
            },
            onFailure = {
                Result.failure(UserManagementException.UserNotFoundException(command.login))
            }
        )
    }
}