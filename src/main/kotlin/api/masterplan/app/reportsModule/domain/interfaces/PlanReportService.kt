package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.dtos.PlanReportDetails
import api.masterplan.app.reportsModule.domain.models.entity.PlanReport
import api.masterplan.app.reportsModule.domain.models.value.*

interface PlanReportService {
    fun getPlanReport(reportId: PlanReportId): PlanReportDetails

    fun updateTaskReport(reportId: PlanReportId, updatedPlanReport: PlanReport): PlanReportId

    fun deletePlanReport(reportId: PlanReportId): PlanReportId

    fun createPlanReport(id: PlanReportId? = null,title: PlanReportTitle, description: PlanReportDescription? = null,
                         employeeId: ReportEmployeeId, taskId: ReportTaskId, documentId: ReportDocumentId): PlanReportId

    fun getCreatedPlanReports(employeeId: ReportEmployeeId): List<PlanReportDetails>

    fun filterCreatedPlanByStatus(employeeId: ReportEmployeeId, status: PlanReportStatus): List<PlanReportDetails>

    fun changePlanReportStatus(reportId: PlanReportId, status: PlanReportStatus): PlanReportId
}