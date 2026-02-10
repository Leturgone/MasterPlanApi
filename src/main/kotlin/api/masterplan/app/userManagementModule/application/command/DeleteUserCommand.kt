package api.masterplan.app.userManagementModule.application.command

import api.masterplan.app.userManagementModule.domain.models.value.UserId

data class DeleteUserCommand(
    val userId: UserId
)
