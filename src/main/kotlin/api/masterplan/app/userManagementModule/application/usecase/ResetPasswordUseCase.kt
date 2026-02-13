package api.masterplan.app.userManagementModule.application.usecase

import api.masterplan.app.userManagementModule.application.command.ResetPasswordCommand
import api.masterplan.app.userManagementModule.domain.interfaces.PasswordHasherService
import api.masterplan.app.userManagementModule.domain.interfaces.UserService
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import org.springframework.stereotype.Service

@Service
class ResetPasswordUseCase(
    val passwordHasherService: PasswordHasherService,
    val userService: UserService
) {
    operator fun invoke(command: ResetPasswordCommand): Result<UserId>{
        return try {
            val hashedPassword = passwordHasherService.hash(command.newPassword)
            val userWithResetPasswordId = userService.resetPasswordForUser(
                userId = command.userId,
                newPassword = hashedPassword)
            return Result.success(userWithResetPasswordId)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}