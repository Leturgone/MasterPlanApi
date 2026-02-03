package api.masterplan.app.authModule.infrastructure.exceptions

import api.masterplan.app.authModule.domain.model.value.UserLogin

sealed class MasterPlanAuthException(message: String): Exception(message) {

    class InvalidCredentials: MasterPlanAuthException(
        "Passwords don't match"
    )
    class InvalidToken: MasterPlanAuthException(
        "Invalid token"
    )

    data class UserNotExistsWithLogin(val login: UserLogin): MasterPlanAuthException(
        "User with login ${login.value} is not exists"
    )

}