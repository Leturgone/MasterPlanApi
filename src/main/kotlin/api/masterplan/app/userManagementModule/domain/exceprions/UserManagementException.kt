package api.masterplan.app.userManagementModule.domain.exceprions

import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin

sealed class UserManagementException(message: String): Exception(message) {

    class UserNotFoundException(val login: UserLogin) : UserManagementException(
        "User with login = ${login.value} not found"
    )

    class InvalidUserCredentialsException : UserManagementException(
        "Invalid credentials"
    )

    class UserNotExistsException(val uid: UserId) : UserManagementException(
        "User with uid = ${uid.value} not exists"
    )
}