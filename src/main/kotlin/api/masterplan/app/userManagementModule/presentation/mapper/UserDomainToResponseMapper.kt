package api.masterplan.app.userManagementModule.presentation.mapper

import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.presentation.dto.responce.UserDataResponse

object UserDomainToResponseMapper {

    fun userToResponse(user: AppUserDetails): UserDataResponse.Success {
        return UserDataResponse.Success(
            id = user.id.value,
            login = user.login.value,
            password = user.password.value,
            roles = user.roles.map { it.name }.toSet(),
        )
    }
}