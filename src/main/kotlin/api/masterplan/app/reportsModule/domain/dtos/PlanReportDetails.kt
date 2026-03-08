package api.masterplan.app.reportsModule.domain.dtos

import api.masterplan.app.reportsModule.domain.models.value.PlanReportDate
import api.masterplan.app.reportsModule.domain.models.value.PlanReportDescription
import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import api.masterplan.app.reportsModule.domain.models.value.PlanReportStatus
import api.masterplan.app.reportsModule.domain.models.value.PlanReportTitle
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportTaskId

data class PlanReportDetails(
    val id: PlanReportId,
    val title: PlanReportTitle,
    val creationDate: PlanReportDate,
    val editDate: PlanReportDate? = null,
    val description: PlanReportDescription? = null,
    val reportStatus: PlanReportStatus,
    val employeeId: ReportEmployeeId,
    val taskId: ReportTaskId,
    val documentId: ReportDocumentId
)