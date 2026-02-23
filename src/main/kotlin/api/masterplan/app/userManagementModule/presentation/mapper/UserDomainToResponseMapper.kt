package api.masterplan.app.userManagementModule.presentation.mapper

import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.presentation.dto.responce.UserDataResponse
import api.masterplan.app.userManagementModule.presentation.dto.responce.UserUidResponse

object UserDomainToResponseMapper {

    fun userToResponse(user: AppUserDetails): UserDataResponse{
        return UserDataResponse(
            id = user.id.value,
            login = user.login.value,
            password = user.password.value,
            roles = user.roles.map { it.name }.toSet(),
        )
    }

    fun idToResponse(id: UserId): UserUidResponse = UserUidResponse(id.value)
}