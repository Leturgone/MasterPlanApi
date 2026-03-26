package api.masterplan.app.apiContracts.userManagement


sealed class UserModuleErrorDto(message: String): Exception(message) {

    class UserNotFoundException(val login: String) : UserModuleErrorDto(
        "User with $login not found"
    )

    class InvalidUserCredentialsException(val errorMessage: String?) : UserModuleErrorDto(
        "Invalid credentials ${errorMessage?.let {": $it"  }}"
    )
    class InternalServerError : UserModuleErrorDto("Internal user module server error")
}