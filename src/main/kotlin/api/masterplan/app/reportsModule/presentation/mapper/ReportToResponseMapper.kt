package api.masterplan.app.reportsModule.presentation.mapper

import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportReferenceId
import api.masterplan.app.reportsModule.domain.models.value.ReportType
import api.masterplan.app.reportsModule.presentation.dto.response.ReportResponse

object ReportToResponseMapper {

    fun toResponse(report: ReportDetails): ReportResponse {
        val refId = when(report.type) {
            ReportType.TASK -> {
                val rep = report.referenceId as ReportReferenceId.ForTask
                rep.taskId.value
            }
            ReportType.PLAN -> {
                val rep = report.referenceId as ReportReferenceId.ForPlan
                rep.planId.value
            }
        }
        return ReportResponse(
            id = report.id.value,
            title = report.title.value,
            creationDate = report.creationDate.value,
            editDate = report.editDate?.value,
            description = report.description?.value,
            reportStatus = report.reportStatus.name,
            employeeId = report.employeeId.value,
            referenceId = refId,
            type = report.type.name,
            documentId = report.documentId.value
        )
    }


    fun toResponse(reportList: List<ReportDetails>): List<ReportResponse> {
        return reportList.map { toResponse(it) }
    }

    fun toResponse(reportId: ReportId) = reportId.value
}