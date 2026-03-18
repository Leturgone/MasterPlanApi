package api.masterplan.app.reportsModule.infrastructure.database.mapper

import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.infrastructure.database.entity.ReportStatusEntity

internal object ReportStatusDatabaseMapper {
    fun toDomain(entity: ReportStatusEntity): ReportStatus {
        return try {
            ReportStatus.valueOf(entity.status.uppercase())
        }catch (_: IllegalArgumentException){
            throw ReportException.InvalidReportStatus(entity.status.uppercase())
        }
    }

    fun toEntity(reportStatusSet: Set<ReportStatusEntity>, reportStatus: ReportStatus): ReportStatusEntity {
        val statusByTitle = reportStatusSet.associateBy { it.status.uppercase() }
        val domainStatus = statusByTitle[reportStatus.name]?:throw ReportException.InvalidReportStatus(reportStatus.name)
        return domainStatus
    }
}