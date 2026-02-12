package api.masterplan.app.userManagementModule.infrastructure.intermodule

import api.masterplan.app.userManagementModule.UserModuleErrorDto
import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException

internal object InterModuleUserToDtoErrorMapper {
    fun toDto(exception: Throwable): UserModuleErrorDto{
        return when (exception) {
            is UserManagementException.InvalidUserCredentialsException -> UserModuleErrorDto.InvalidUserCredentialsException()
            is UserManagementException.UserNotFoundException -> UserModuleErrorDto.UserNotFoundException(exception.login.value)
            else -> UserModuleErrorDto.InternalServerError()
        }
    }
}