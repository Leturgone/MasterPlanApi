package api.masterplan.app.userManagementModule.application.usecase

import api.masterplan.app.userManagementModule.application.command.GetUserByIdCommand
import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.interfaces.UserService

class GetUserByIdUseCase(
    private val userService: UserService
) {
    operator fun invoke(command: GetUserByIdCommand): Result<AppUserDetails> {
        return try {
            val user = userService.getUserById(command.userId)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}