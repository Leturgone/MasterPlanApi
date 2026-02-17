package api.masterplan.app.userManagementModule.domain.exceprions

import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin

sealed class UserManagementException(message: String): Exception(message) {

    class UserNotFoundException(val login: UserLogin) : UserManagementException(
        "User with login = ${login.value} not found"
    )

    class InvalidUserCredentialsException(val errorMessage: String? = null) : UserManagementException(
        "Invalid credentials ${errorMessage?.let {": $it"  }}"
    )

    class UserNotExistsException(val uid: UserId) : UserManagementException(
        "User with uid = ${uid.value} not exists"
    )

    class UserCantBeDeleted(val userId: UserId) : UserManagementException(
        "You dont have permission to delete user with id = ${userId.value}"
    )

    class UserAlreadyExistsException(val login: UserLogin) : UserManagementException(
        "User with login = ${login.value} already exists"
    )

    class FailedToCreateUserException(val login: UserLogin) : UserManagementException(
        "Failed to create user with login = ${login.value} "
    )

    class FailedToDeleteUserException(val uid: UserId) : UserManagementException(
        "Failed to delete user with id = ${uid.value} "
    )

    class FailedToResetPasswordForUser(val uid: UserId) : UserManagementException(
        "Failed to reset password for user with uid = ${uid.value} "
    )

    class InvalidRoleTitle(val title: String) : UserManagementException(
        "Invalid role title = $title"
    )
}