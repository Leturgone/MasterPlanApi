package api.masterplan.app.userManagementModule.infrastructure.intermodule

import api.masterplan.app.userManagementModule.UserCredentialsDto
import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails

internal object InterModuleUserToDtoSuccessMapper {
    fun toDto(user: AppUserDetails): UserCredentialsDto{
        return UserCredentialsDto(
            userId = user.id.value,
            password = user.password.value,
            login = user.login.value,
            roles = user.roles.map { it.name }.toSet()
        )
    }
}