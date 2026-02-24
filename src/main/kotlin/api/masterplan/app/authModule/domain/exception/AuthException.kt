package api.masterplan.app.authModule.domain.exception

import api.masterplan.app.authModule.domain.model.value.AuthUserLogin

sealed class AuthException(message: String): Exception(message) {

    class InvalidCredentials: AuthException(
        "Passwords don't match"
    )

    class InvalidLoginOrPassword(errMassage: String): AuthException(errMassage)

    class InvalidToken: AuthException(
        "Invalid token"
    )

    data class UserNotExistsWithLogin(val login: AuthUserLogin): AuthException(
        "User with login ${login.value} is not exists"
    )

    data class TokenGenerationException(val authUserName: AuthUserLogin, val emessage: String?): AuthException(
        "Exception while generating token for user ${authUserName.value} : $emessage "
    )

    class InternalServerError(message: String) : AuthException(message)

}