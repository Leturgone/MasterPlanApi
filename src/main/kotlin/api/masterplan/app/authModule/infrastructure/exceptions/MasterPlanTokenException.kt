package api.masterplan.app.authModule.infrastructure.exceptions

import api.masterplan.app.authModule.domain.model.value.UserId

sealed class MasterPlanTokenException(message: String): Exception(message) {

    data class TokenGenerationException(val userId: UserId, val emessage: String?): MasterPlanDatabaseException(
        "Exception while generating token for user ${userId.value} : $emessage "
    )

}