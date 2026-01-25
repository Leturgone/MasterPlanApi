package api.masterplan.app.authModule.infrastructure.exceptions

import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserLogin

sealed class MasterPlanDatabaseException(message: String): Exception(message) {

    data class InvalidRoleTitle(val rawTitle: String?) : MasterPlanDatabaseException(
        "Invalid role: '$rawTitle'"
    )



    data class UserNotExistsWithId(val id: UserId): MasterPlanDatabaseException(
        "User with id ${id.value} is not exists"
    )


}