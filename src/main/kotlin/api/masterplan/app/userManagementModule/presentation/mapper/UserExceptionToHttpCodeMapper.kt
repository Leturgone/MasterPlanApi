package api.masterplan.app.userManagementModule.presentation.mapper

import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import org.springframework.http.HttpStatus

object UserExceptionToHttpCodeMapper {
    fun exceptionToHttpCode(ex: Throwable): HttpStatus {
        return when (ex) {
            is UserManagementException.UserNotFoundException -> HttpStatus.NOT_FOUND
            is UserManagementException.UserNotExistsException -> HttpStatus.NOT_FOUND
            is UserManagementException.UserAlreadyExistsException -> HttpStatus.CONFLICT
            is UserManagementException.FailedToCreateUserException -> HttpStatus.INTERNAL_SERVER_ERROR
            is UserManagementException.InvalidUserCredentialsException -> HttpStatus.BAD_REQUEST
            is UserManagementException.InvalidRoleTitle -> HttpStatus.BAD_REQUEST
            is UserManagementException.FailedToResetPasswordForUser -> HttpStatus.INTERNAL_SERVER_ERROR
            is UserManagementException.FailedToDeleteUserException -> HttpStatus.INTERNAL_SERVER_ERROR
            is UserManagementException.UserCantBeDeleted -> HttpStatus.FORBIDDEN
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
}