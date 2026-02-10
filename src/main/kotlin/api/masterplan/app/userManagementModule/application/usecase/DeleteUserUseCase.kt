package api.masterplan.app.userManagementModule.application.usecase

import api.masterplan.app.userManagementModule.application.command.DeleteUserCommand
import api.masterplan.app.userManagementModule.domain.interfaces.UserService
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import org.springframework.stereotype.Service

@Service
class DeleteUserUseCase(
    private val userService: UserService
) {
    operator fun invoke(command: DeleteUserCommand): Result<UserId> {
        return userService.deleteUser(userId = command.userId)
    }
}