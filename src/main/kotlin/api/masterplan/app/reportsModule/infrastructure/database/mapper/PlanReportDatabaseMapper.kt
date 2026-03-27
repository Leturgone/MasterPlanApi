package api.masterplan.app.reportsModule.infrastructure.database.mapper

import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportDate
import api.masterplan.app.reportsModule.domain.models.value.ReportDescription
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportPlanId
import api.masterplan.app.reportsModule.domain.models.value.ReportReferenceId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import api.masterplan.app.reportsModule.infrastructure.database.entity.PlanReportEntity
import api.masterplan.app.reportsModule.infrastructure.database.entity.ReportStatusEntity

object PlanReportDatabaseMapper {

    fun toDomain(entity: PlanReportEntity): Report {
        val domainStatus = ReportStatusDatabaseMapper.toDomain(entity.reportStatus)
        return Report.create(
            id = ReportId(entity.id),
            title = ReportTitle.validate(entity.title),
            creationDate = ReportDate(entity.creationDate),
            editDate = entity.editDate?.let { ReportDate(it) },
            description = entity.description?.let { ReportDescription.validate(it)},
            reportStatus = domainStatus,
            employeeId = ReportEmployeeId(entity.employeeId),
            referenceId = ReportReferenceId.ForPlan(ReportPlanId(entity.planId)),
            documentId = ReportDocumentId(entity.documentId),
        )
    }

    fun toDomain(entities: List<PlanReportEntity>): List<Report> {
        return entities.map { toDomain(it) }
    }

    fun toEntity(report: Report,statusSet: Set<ReportStatusEntity>): PlanReportEntity{
        val statusEntity = ReportStatusDatabaseMapper.toEntity(statusSet,report.reportStatus)
        val planId = when (report.referenceId) {
            is ReportReferenceId.ForPlan -> report.referenceId.planId.value
            is ReportReferenceId.ForTask -> throw ReportException.InvalidReferenceId(report.referenceId)
        }
        return PlanReportEntity(
            id = report.id.value,
            title = report.title.value,
            creationDate = report.creationDate.value,
            editDate = report.editDate?.value,
            description = report.description?.value,
            reportStatus = statusEntity,
            employeeId = report.employeeId.value,
            planId = planId,
            documentId = report.documentId.value
        )
    }
}