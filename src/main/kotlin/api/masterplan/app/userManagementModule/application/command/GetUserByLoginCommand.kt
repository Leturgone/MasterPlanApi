package api.masterplan.app.userManagementModule.application.command

import api.masterplan.app.userManagementModule.domain.models.value.UserLogin


data class GetUserByLoginCommand(
    val login: UserLogin
)
