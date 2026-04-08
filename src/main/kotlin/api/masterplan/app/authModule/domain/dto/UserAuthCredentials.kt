package api.masterplan.app.authModule.domain.dto

import api.masterplan.app.authModule.domain.model.value.AuthUserId
import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserRole

data class UserAuthCredentials(
    val userId: AuthUserId,
    val authUserName: AuthUserLogin,
    val roles: Set<AuthUserRole>
)
