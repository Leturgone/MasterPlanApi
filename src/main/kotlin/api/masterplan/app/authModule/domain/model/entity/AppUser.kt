package api.masterplan.app.authModule.domain.model.entity

import api.masterplan.app.authModule.domain.model.value.UserId
import api.masterplan.app.authModule.domain.model.value.UserLogin
import api.masterplan.app.authModule.domain.model.value.UserPassword
import api.masterplan.app.authModule.domain.model.value.UserRole

@ConsistentCopyVisibility
data class AppUser private constructor(
    val id: UserId,
    val login: UserLogin,
    val password: UserPassword,
    val roles: Set<UserRole>
){
    companion object {
        fun create(login: String, rawPassword: String, roles: Set<UserRole> = setOf(UserRole.EMPLOYEE)): AppUser{
            return AppUser(
                id = UserId.generate(),
                login = UserLogin.create(login),
                password = UserPassword.create(rawPassword),
                roles = roles
            )
        }
    }

    fun isAdmin() = roles.contains(UserRole.ADMIN)

    fun isDirector() = roles.contains(UserRole.DIRECTOR)

    fun isEmployee() = roles.contains(UserRole.EMPLOYEE)

}