package api.masterplan.app.userManagementModule.domain.interfaces

import api.masterplan.app.userManagementModule.domain.dtos.AppUserDetails
import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole


interface UserRepository {

    fun findById(id: UserId): AppUser?

    fun findByLogin(login: UserLogin): AppUser?

    fun isUserExist(login: UserLogin): Boolean

    fun editUser(userId: UserId,newUserData: AppUserDetails): UserId?

    fun createUser(login: UserLogin,password: UserPassword, roles: Set<UserRole>): UserId?

    fun getUser(userId: UserId): AppUser?

    fun deleteUser(userId: UserId): UserId?
}