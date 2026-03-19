package api.masterplan.app.reportsModule.infrastructure.database.mapper

import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.*
import api.masterplan.app.reportsModule.infrastructure.database.entity.ReportStatusEntity
import api.masterplan.app.reportsModule.infrastructure.database.entity.TaskReportEntity

object TaskReportDatabaseMapper {
    fun toDomain(entity: TaskReportEntity): Report {
        val domainStatus = ReportStatusDatabaseMapper.toDomain(entity.reportStatus)
        return Report.create(
            id = ReportId(entity.id),
            title = ReportTitle.validate(entity.title),
            description = entity.description?.let { ReportDescription.validate(it)},
            employeeId = ReportEmployeeId(entity.employeeId),
            referenceId = ReportReferenceId.ForTask(ReportTaskId(entity.taskId)),
            documentId = ReportDocumentId(entity.documentId),
        ).changeReportStatus(domainStatus)
    }

    fun toDomain(entities: List<TaskReportEntity>): List<Report> {
        return entities.map { toDomain(it) }
    }

    fun toEntity(report: Report,statusSet: Set<ReportStatusEntity>): TaskReportEntity {
        val statusEntity = ReportStatusDatabaseMapper.toEntity(statusSet,report.reportStatus)
        val taskId = when (report.referenceId) {
            is ReportReferenceId.ForPlan ->  throw ReportException.InvalidReferenceId(report.referenceId)
            is ReportReferenceId.ForTask -> report.referenceId.taskId.value
        }
        return TaskReportEntity(
            id = report.id.value,
            title = report.title.value,
            creationDate = report.creationDate.value,
            editDate = report.editDate?.value,
            description = report.description?.value,
            reportStatus = statusEntity,
            employeeId = report.employeeId.value,
            taskId = taskId,
            documentId = report.documentId.value
        )
    }
}