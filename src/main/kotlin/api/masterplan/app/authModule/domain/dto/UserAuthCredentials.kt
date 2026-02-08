package api.masterplan.app.authModule.domain.dto

import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.AuthUserRole

data class UserAuthCredentials(
    val authUserId: AuthUserId,
    val roles: Set<AuthUserRole>
)
