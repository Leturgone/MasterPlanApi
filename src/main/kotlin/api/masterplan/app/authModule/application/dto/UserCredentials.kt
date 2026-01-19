package api.masterplan.app.authModule.application.dto

import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserRole

data class UserCredentials(
    val userId: UserId,
    val roles: Set<UserRole>
)
