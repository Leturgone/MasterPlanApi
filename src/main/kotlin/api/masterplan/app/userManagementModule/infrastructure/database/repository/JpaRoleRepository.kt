package api.masterplan.app.userManagementModule.infrastructure.database.repository

import api.masterplan.app.userManagementModule.infrastructure.database.entity.RoleEntity
import org.springframework.data.repository.CrudRepository

interface JpaRoleRepository: CrudRepository<RoleEntity, Int> {

}