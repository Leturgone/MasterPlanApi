package api.masterplan.app.userManagementModule.application.service



import api.masterplan.app.userManagementModule.application.dto.AppUserDetails
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin

interface UserService {
    fun getUserByLogin(login: UserLogin): Result<AppUserDetails>
}