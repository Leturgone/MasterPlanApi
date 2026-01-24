package api.masterplan.app.authModule.infrastructure.database.mapper

import api.masterplan.app.authModule.domain.model.value.UserRole
import api.masterplan.app.authModule.infrastructure.database.entity.RoleEntity
import api.masterplan.app.authModule.infrastructure.exceptions.MasterPlanDatabaseException

object RoleEntityMapper {
    fun toDomain(databaseRoles: MutableSet<RoleEntity>): Set<UserRole>{
        return databaseRoles.map { entity ->
            try {
                UserRole.valueOf(entity.title.uppercase())
            }catch (_: IllegalArgumentException){
                throw MasterPlanDatabaseException.InvalidRoleTitle(entity.title)
            }
        }.toSet()
    }
}