package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.*

interface ReportService {

    fun getReport(reportId: ReportId, reportType: ReportType): ReportDetails

    fun updateReport(reportId: ReportId, reportType: ReportType, updatedReport: Report): ReportId

    fun deleteReport(reportId: ReportId,reportType: ReportType): ReportId

    fun createReport(id: ReportId? = null, title: ReportTitle, description: ReportDescription? = null,
                         employeeId: ReportEmployeeId, referenceId: ReportReferenceId,
                     documentId: ReportDocumentId): ReportId

    fun getCreatedReports(employeeId: ReportEmployeeId,reportType: ReportType): List<ReportDetails>

    fun filterCreatedReportsByStatus(employeeId: ReportEmployeeId ,reportType: ReportType,
                                     status: ReportStatus): List<ReportDetails>

    fun changeReportStatus(reportId: ReportId, reportType: ReportType, status: ReportStatus): ReportId

    fun getSubordinatesTaskReports(subordinatesIds:Set<ReportEmployeeId>): List<ReportDetails>

    fun filterSubordinatesTaskReportsByStatus(subordinatesIds:Set<ReportEmployeeId>,
                                              status: ReportStatus): List<ReportDetails>

}