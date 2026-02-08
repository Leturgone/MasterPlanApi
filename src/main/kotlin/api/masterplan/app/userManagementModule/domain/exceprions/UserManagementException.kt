package api.masterplan.app.userManagementModule.domain.exceprions

import api.masterplan.app.userManagementModule.domain.models.value.UserLogin

sealed class UserManagementException(message: String): Exception(message) {

    class UserNotFoundException(val login: UserLogin) : UserManagementException(
        "User with ${login.value} not found"
    )

    class InvalidUserCredentialsException : UserManagementException(
        "Invalid credentials"
    )
}