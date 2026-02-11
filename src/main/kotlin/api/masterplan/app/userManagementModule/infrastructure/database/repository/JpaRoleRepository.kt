package api.masterplan.app.userManagementModule.infrastructure.database.repository

import api.masterplan.app.userManagementModule.infrastructure.database.entity.RoleEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaRoleRepository: CrudRepository<RoleEntity, Int> {

    fun findByTitle(title: String): RoleEntity?

}