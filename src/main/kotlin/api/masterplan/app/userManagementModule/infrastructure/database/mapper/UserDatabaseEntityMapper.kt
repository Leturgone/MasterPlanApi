package api.masterplan.app.userManagementModule.infrastructure.database.mapper

import api.masterplan.app.userManagementModule.domain.models.entity.AppUser
import api.masterplan.app.userManagementModule.domain.models.value.UserId
import api.masterplan.app.userManagementModule.domain.models.value.UserLogin
import api.masterplan.app.userManagementModule.domain.models.value.UserPassword
import api.masterplan.app.userManagementModule.infrastructure.database.entity.AppUserEntity
import api.masterplan.app.userManagementModule.infrastructure.database.entity.RoleEntity

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

    fun toEntity(domainUser: AppUser,databaseRoles: HashSet<RoleEntity>): AppUserEntity{
        val entityRoles = RoleDatabaseMapper.toEntity(domainUser.roles, databaseRoles)
        return AppUserEntity(
            id = domainUser.id.value,
            login = domainUser.login.value,
            passwordHash = domainUser.password.value,
            roles = entityRoles
        )
    }
}