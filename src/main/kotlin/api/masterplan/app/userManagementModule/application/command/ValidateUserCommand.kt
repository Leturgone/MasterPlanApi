package api.masterplan.app.userManagementModule.application.command

import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword

data class ValidateUserCommand(
    val login: UserLogin,
    val password: UserPassword
)
