package api.masterplan.app.authModule.application.commands

import api.masterplan.app.authModule.domain.model.value.UserLogin
import api.masterplan.app.authModule.domain.model.value.UserPassword

data class LoginCommand (
    val login: UserLogin,
    val password: UserPassword
)
