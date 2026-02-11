package api.masterplan.app.userManagementModule.domain.models.entity

import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole

@ConsistentCopyVisibility
data class AppUser private constructor(
    val id: UserId,
    val login: UserLogin,
    val password: UserPassword,
    val roles: Set<UserRole>
){
    companion object {
        fun create(uid: UserId? = null, login: UserLogin, rawPassword: UserPassword, roles: Set<UserRole> = setOf(UserRole.EMPLOYEE)): AppUser{
            return AppUser(
                id = uid?: UserId.generate(),
                login = login,
                password = rawPassword,
                roles = roles
            )
        }
    }

    fun isAdmin() = roles.contains(UserRole.ADMIN)

    fun isDirector() = roles.contains(UserRole.DIRECTOR)

    fun isEmployee() = roles.contains(UserRole.EMPLOYEE)

}