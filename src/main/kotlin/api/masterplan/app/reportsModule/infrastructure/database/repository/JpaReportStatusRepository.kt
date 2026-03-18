package api.masterplan.app.reportsModule.infrastructure.database.repository

import api.masterplan.app.reportsModule.infrastructure.database.entity.ReportStatusEntity
import org.springframework.data.repository.CrudRepository

interface JpaReportStatusRepository: CrudRepository<ReportStatusEntity, Int> {
}