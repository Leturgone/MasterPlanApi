package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.dtos.PlanReportDetails
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.*

interface ReportService {

    fun getReport(reportId: ReportId, reportType: ReportType): PlanReportDetails

    fun updateReport(reportId: ReportId, reportType: ReportType, updatedPlanReport: Report): ReportId

    fun deleteReport(reportId: ReportId,reportType: ReportType): ReportId

    fun createReport(id: ReportId? = null, title: ReportTitle, description: ReportDescription? = null,
                         employeeId: ReportEmployeeId, referenceId: ReportReferenceId,
                     documentId: ReportDocumentId): ReportId

    fun getCreatedReports(employeeId: ReportEmployeeId,reportType: ReportType): List<PlanReportDetails>

    fun filterCreatedReportsByStatus(employeeId: ReportEmployeeId ,reportType: ReportType,
                                     status: ReportStatus): List<PlanReportDetails>

    fun changeReportStatus(reportId: ReportId, reportType: ReportType, status: ReportStatus): ReportId

    fun getSubordinatesTaskReports(subordinatesIds:Set<ReportEmployeeId>): List<TaskReportDetails>

    fun filterSubordinatesTaskReportsByStatus(subordinatesIds:Set<ReportEmployeeId>,
                                              status: ReportStatus): List<TaskReportDetails>

}