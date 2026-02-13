package api.masterplan.app.userManagementModule.application.usecase

import api.masterplan.app.userManagementModule.application.command.GetUserByLoginCommand
import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.interfaces.UserService
import org.springframework.stereotype.Service

@Service
class GetUserByLoginUseCase(
    private val userService: UserService
) {
    operator fun invoke(command: GetUserByLoginCommand): Result<AppUserDetails>{
        return try {
            val user = userService.getUserByLogin(command.login)
            Result.success(user)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}