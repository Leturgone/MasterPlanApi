package api.masterplan.app.userManagementModule.application.usecase

import api.masterplan.app.userManagementModule.application.command.GetUserByLoginCommand
import api.masterplan.app.userManagementModule.application.dto.AppUserDetails
import api.masterplan.app.userManagementModule.application.service.UserService


class GetUserByLoginUseCase(
    private val userService: UserService
) {
    operator fun invoke(command: GetUserByLoginCommand): Result<AppUserDetails>{
        return userService.getUserByLogin(command.login).fold(
            onSuccess = { Result.success(it)},
            onFailure = { Result.failure(it) }
        )
    }
}