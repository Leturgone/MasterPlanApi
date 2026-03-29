package api.masterplan.app.adminRequestsModule.infrastructure.database.repository

import api.masterplan.app.adminRequestsModule.infrastructure.database.entity.AdminRequestStatusEntity
import org.springframework.data.repository.CrudRepository

interface JpaAdminRequestStatusRepository: CrudRepository<AdminRequestStatusEntity, Int> {
}