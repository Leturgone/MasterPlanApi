package api.masterplan.app.userManagementModule.domain.interfaces

import api.masterplan.app.authModule.domain.model.entity.AppUser
import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserLogin

interface UserRepository {

    fun findById(id: UserId): AppUser?

    fun findByLogin(login: UserLogin): AppUser?

    fun isUserExist(login: UserLogin): Boolean

}