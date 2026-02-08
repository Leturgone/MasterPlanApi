package api.masterplan.app.userManagementModule

sealed class UserModuleErrorDto(message: String): Exception(message) {

    class UserNotFoundException(val login: String) : UserModuleErrorDto(
        "User with $login not found"
    )

    class InvalidUserCredentialsException : UserModuleErrorDto(
        "Invalid credentials"
    )
    class InternalServerError : UserModuleErrorDto("Internal user module server error")
}