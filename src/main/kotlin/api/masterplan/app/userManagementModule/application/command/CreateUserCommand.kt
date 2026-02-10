package api.masterplan.app.userManagementModule.application.command

import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole

data class CreateUserCommand(
    val login: UserLogin,
    val password: UserPassword,
    val roles: Set<UserRole>
)