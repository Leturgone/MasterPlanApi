package api.masterplan.app.authModule.application.dto

import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.AuthUserRole

data class JwtToken(
    val token: String,
    val authUserId: AuthUserId,
    val roles: Set<AuthUserRole>
)
