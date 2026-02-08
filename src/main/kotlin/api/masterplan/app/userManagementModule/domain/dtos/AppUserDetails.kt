package api.masterplan.app.userManagementModule.domain.dtos

import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole

data class AppUserDetails(
    val id: UserId,
    val login: UserLogin,
    val password: UserPassword,
    val roles: Set<UserRole>
)