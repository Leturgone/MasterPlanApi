package api.masterplan.app.authModule.infrastructure.adapters

import api.masterplan.app.authModule.domain.exception.AuthException
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.userManagementModule.UserModuleErrorDto

internal object AuthInnerModuleErrorMapper {
    fun exceptionToModuleException(ex: Throwable): AuthException {
        return when (ex) {
            is UserModuleErrorDto.UserNotFoundException -> AuthException.UserNotExistsWithLogin(AuthUserLogin.validate(ex.login))
            is UserModuleErrorDto.InvalidUserCredentialsException -> AuthException.InvalidCredentials()
            else -> AuthException.InternalServerError()
        }
    }
}