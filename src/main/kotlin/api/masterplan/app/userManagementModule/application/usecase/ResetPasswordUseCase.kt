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
        val hashedPassword = passwordHasherService.hash(command.newPassword)
        return userService.resetPasswordForUser(userId = command.userId,hashedPassword)
    }
}