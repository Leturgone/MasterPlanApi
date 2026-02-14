package api.masterplan.app.userManagementModule.application.usecase

import api.masterplan.app.userManagementModule.application.command.CreateUserCommand
import api.masterplan.app.userManagementModule.domain.interfaces.PasswordHasherService
import api.masterplan.app.userManagementModule.domain.interfaces.UserService
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(
    private val userService: UserService,
    private val passwordHasherService: PasswordHasherService
) {
    operator fun invoke(command: CreateUserCommand): Result<UserId> {
        return try {
            val hashedPassword = passwordHasherService.hash(command.password)
            val userId = userService.createUser(
                login = command.login,
                password = hashedPassword,
                roles = command.roles
            )
            Result.success(userId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}