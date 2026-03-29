package api.masterplan.app.adminRequestsModule.infrastructure.database.repository

import api.masterplan.app.adminRequestsModule.infrastructure.database.entity.AdminRequestEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface JpaAdminRequestRepository: CrudRepository<AdminRequestEntity, UUID> {

    fun findBySenderId(senderId: UUID): List<AdminRequestEntity>

}