package api.masterplan.app.userManagementModule.domain.interfaces

import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole

interface UserService {

    fun getUserByLogin(login: UserLogin): Result<AppUserDetails>

    fun editUser(userId: UserId,newUserData: AppUserDetails): Result<UserId>

    fun createUser(login: UserLogin,password: UserPassword, roles: Set<UserRole>): Result<UserId>

    fun getUser(userId: UserId): Result<AppUserDetails>

    fun deleteUser(userId: UserId): Result<UserId>
}