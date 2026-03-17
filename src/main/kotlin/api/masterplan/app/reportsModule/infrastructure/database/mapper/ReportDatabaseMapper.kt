package api.masterplan.app.reportsModule.infrastructure.database.mapper

import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportDescription
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportPlanId
import api.masterplan.app.reportsModule.domain.models.value.ReportReferenceId
import api.masterplan.app.reportsModule.domain.models.value.ReportTaskId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import api.masterplan.app.reportsModule.infrastructure.database.entity.PlanReportEntity
import api.masterplan.app.reportsModule.infrastructure.database.entity.TaskReportEntity

object ReportDatabaseMapper {

    fun toDomain(entity: PlanReportEntity): Report {
        val domainStatus = ReportStatusDatabaseMapper.toDomain(entity.reportStatus)
        return Report.create(
            id = ReportId(entity.id),
            title = ReportTitle.validate(entity.title),
            description = ReportDescription.validate(entity.description),
            employeeId = ReportEmployeeId(entity.employeeId),
            referenceId = ReportReferenceId.ForPlan(ReportPlanId(entity.planId)),
            documentId = ReportDocumentId(entity.documentId),
        ).changeReportStatus(domainStatus)
    }

    fun toDomain(entity: TaskReportEntity): Report {
        val domainStatus = ReportStatusDatabaseMapper.toDomain(entity.reportStatus)
        return Report.create(
            id = ReportId(entity.id),
            title = ReportTitle.validate(entity.title),
            description = ReportDescription.validate(entity.description),
            employeeId = ReportEmployeeId(entity.employeeId),
            referenceId = ReportReferenceId.ForTask(ReportTaskId(entity.taskId)),
            documentId = ReportDocumentId(entity.documentId),
        ).changeReportStatus(domainStatus)
    }


}