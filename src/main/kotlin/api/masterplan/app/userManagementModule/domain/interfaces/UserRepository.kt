package api.masterplan.app.userManagementModule.domain.interfaces

import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword


interface UserRepository {

    fun findByLogin(login: UserLogin): AppUser?

    fun isUserExist(login: UserLogin): Boolean

    fun setPassword(userId: UserId,newPassword: UserPassword): UserId?

    fun saveUser(user: AppUser): UserId?

    fun getUser(userId: UserId): AppUser?

    fun deleteUser(userId: UserId): UserId?
}