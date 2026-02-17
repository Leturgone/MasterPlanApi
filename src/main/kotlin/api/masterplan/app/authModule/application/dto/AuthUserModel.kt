package api.masterplan.app.authModule.application.dto

import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword
import api.masterplan.app.authModule.domain.model.value.AuthUserRole


data class AuthUserModel(
    val login: AuthUserLogin,
    val password: AuthUserPassword,
    val roles: Set<AuthUserRole>
)
