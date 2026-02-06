package api.masterplan.app.authModule.domain.exception

import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin

sealed class AuthException(message: String): Exception(message) {

    class InvalidCredentials: AuthException(
        "Passwords don't match"
    )
    class InvalidToken: AuthException(
        "Invalid token"
    )

    data class UserNotExistsWithLogin(val login: AuthUserLogin): AuthException(
        "User with login ${login.value} is not exists"
    )

    data class TokenGenerationException(val authUserId: AuthUserId, val emessage: String?): AuthException(
        "Exception while generating token for user ${authUserId.value} : $emessage "
    )

    class InternalServerError : AuthException("Internal user module server error")

}