package api.masterplan.app.reportsModule.infrastructure.database.repository

import api.masterplan.app.reportsModule.infrastructure.database.entity.TaskReportEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JpaTaskReportRepository: CrudRepository<TaskReportEntity, UUID>  {
}