package api.masterplan.app.authModule.application.command

import api.masterplan.app.authModule.domain.model.value.AuthUserLogin
import api.masterplan.app.authModule.domain.model.value.AuthUserPassword

data class LoginCommand (
    val login: AuthUserLogin,
    val password: AuthUserPassword
)
