package api.masterplan.app.adminRequestsModule.infrastructure.database.repository

import api.masterplan.app.adminRequestsModule.infrastructure.database.entity.AdminAnswerEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JpaAdminAnswerRepository: CrudRepository<AdminAnswerEntity, UUID>  {

    fun findByAdminRequestId(adminRequestId: UUID): AdminAnswerEntity?

}