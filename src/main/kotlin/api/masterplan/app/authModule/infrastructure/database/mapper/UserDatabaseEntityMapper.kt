package api.masterplan.app.authModule.infrastructure.database.mapper

import api.masterplan.app.authModule.domain.model.entity.AppUser
import api.masterplan.app.authModule.infrastructure.database.entity.AppUserEntity

object UserDatabaseEntityMapper {
    fun toDomain(databaseEntity: AppUserEntity): AppUser {
        val domainRoles = RoleDatabaseMapper.toDomain(databaseEntity.roles)

        return AppUser.create(
            uid = databaseEntity.uid,
            login = databaseEntity.login,
            rawPassword = databaseEntity.passwordHash,
            roles = domainRoles
        )
    }
}