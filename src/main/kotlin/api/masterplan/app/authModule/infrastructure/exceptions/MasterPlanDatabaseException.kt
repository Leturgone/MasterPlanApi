package api.masterplan.app.authModule.infrastructure.exceptions

import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserLogin

sealed class MasterPlanDatabaseException(message: String): Exception(message) {

    data class InvalidRoleTitle(val rawTitle: String?) : MasterPlanDatabaseException(
        "Invalid role: '$rawTitle'"
    )

    data class UserNotExistsWithLogin(val login: UserLogin): MasterPlanDatabaseException(
        "User with login ${login.value} is not exists"
    )

    data class UserNotExistsWithId(val id: UserId): MasterPlanDatabaseException(
        "User with id ${id.value} is not exists"
    )


}