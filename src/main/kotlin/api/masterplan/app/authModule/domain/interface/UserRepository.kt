package api.masterplan.app.authModule.domain.`interface`

import api.masterplan.app.authModule.domain.model.entity.AppUser
import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserLogin

interface UserRepository {

    suspend fun findById(id: UserId): Result<AppUser>

    suspend fun findByLogin(login: UserLogin): Result<AppUser>

    suspend fun isUserExist(login: UserLogin): Boolean

}