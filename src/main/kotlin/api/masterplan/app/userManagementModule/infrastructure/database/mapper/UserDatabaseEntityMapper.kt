package api.masterplan.app.userManagementModule.infrastructure.database.mapper

import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.infrastructure.database.entity.AppUserEntity

object UserDatabaseEntityMapper {
    fun toDomain(databaseEntity: AppUserEntity): AppUser {
        val domainRoles = RoleDatabaseMapper.toDomain(databaseEntity.roles)
        return AppUser.create(
            uid = UserId(databaseEntity.id),
            login = UserLogin(databaseEntity.login),
            rawPassword = UserPassword(databaseEntity.passwordHash),
            roles = domainRoles
        )
    }
}