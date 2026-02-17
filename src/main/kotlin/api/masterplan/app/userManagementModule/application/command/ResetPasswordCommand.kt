package api.masterplan.app.userManagementModule.application.command

import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword

data class ResetPasswordCommand(
    val userId: UserId,
    val newPassword: UserPassword
)
