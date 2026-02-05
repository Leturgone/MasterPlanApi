package api.masterplan.app.userManagementModule.domain.interfaces

import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin


interface UserRepository {

    fun findById(id: UserId): AppUser?

    fun findByLogin(login: UserLogin): AppUser?

    fun isUserExist(login: UserLogin): Boolean

}