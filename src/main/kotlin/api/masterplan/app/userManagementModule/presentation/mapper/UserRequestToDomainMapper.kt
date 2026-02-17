package api.masterplan.app.userManagementModule.presentation.mapper

import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.domain.models.value.UserRole
import java.util.UUID

object UserRequestToDomainMapper {

    fun idToDomain(id: UUID) = UserId(id)

    fun loginToDomain(login: String) = UserLogin.validate(login)

    fun passwordToDomain(password: String) = UserPassword.validate(password)

    fun rolesToDomain(roles: Set<String>): Set<UserRole> {
        return roles.map {
            try {
                UserRole.valueOf(it.uppercase())
            }catch (_: IllegalArgumentException){
                throw UserManagementException.InvalidRoleTitle(it)
            }
        }.toSet()
    }

}