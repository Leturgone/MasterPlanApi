package api.masterplan.app.userManagementModule.infrastructure.database.mapper

import api.masterplan.app.userManagementModule.domain.exceprions.UserManagementException
import api.masterplan.app.userManagementModule.domain.models.value.UserRole
import api.masterplan.app.userManagementModule.infrastructure.database.entity.RoleEntity

object RoleDatabaseMapper {
    fun toDomain(databaseRoles: MutableSet<RoleEntity>): Set<UserRole>{
        return databaseRoles.map { entity ->
            try {
                UserRole.valueOf(entity.title.uppercase())
            }catch (_: IllegalArgumentException){
                throw UserManagementException.InvalidRoleTitle(entity.title.uppercase())
            }
        }.toSet()
    }
}