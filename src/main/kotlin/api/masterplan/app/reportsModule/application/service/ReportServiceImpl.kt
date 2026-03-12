package api.masterplan.app.reportsModule.application.service

import api.masterplan.app.logging.LoggingMethod
import api.masterplan.app.reportsModule.domain.dtos.PlanReportDetails
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportRepository
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportRepository
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.ReportDescription
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportReferenceId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import api.masterplan.app.reportsModule.domain.models.value.ReportType
import org.springframework.stereotype.Service

@Service
class ReportServiceImpl(
    private val planReportRepository: PlanReportRepository,
    private val taskReportRepository: TaskReportRepository
): ReportService {

    @LoggingMethod("reportModule")
    override fun getReport(reportId: ReportId, reportType: ReportType): PlanReportDetails {
        TODO("Not yet implemented")
    }


    @LoggingMethod("reportModule")
    override fun updateReport(reportId: ReportId, reportType: ReportType, updatedPlanReport: Report): ReportId {
        TODO("Not yet implemented")
    }


    @LoggingMethod("reportModule")
    override fun deleteReport(reportId: ReportId, reportType: ReportType): ReportId {
        TODO("Not yet implemented")
    }


    @LoggingMethod("reportModule")
    override fun createReport(id: ReportId?, title: ReportTitle, description: ReportDescription?,
        employeeId: ReportEmployeeId, referenceId: ReportReferenceId, documentId: ReportDocumentId): ReportId {
        TODO("Not yet implemented")
    }


    @LoggingMethod("reportModule")
    override fun getCreatedReports(employeeId: ReportEmployeeId, reportType: ReportType): List<PlanReportDetails> {
        TODO("Not yet implemented")
    }


    @LoggingMethod("reportModule")
    override fun filterCreatedReportsByStatus(employeeId: ReportEmployeeId, reportType: ReportType, status: ReportStatus): List<PlanReportDetails> {
        TODO("Not yet implemented")
    }


    @LoggingMethod("reportModule")
    override fun changeReportStatus(reportId: ReportId, reportType: ReportType, status: ReportStatus): ReportId {
        TODO("Not yet implemented")
    }


    @LoggingMethod("reportModule")
    override fun getSubordinatesTaskReports(subordinatesIds: Set<ReportEmployeeId>): List<TaskReportDetails> {
        TODO("Not yet implemented")
    }


    @LoggingMethod("reportModule")
    override fun filterSubordinatesTaskReportsByStatus(subordinatesIds: Set<ReportEmployeeId>, status: ReportStatus): List<TaskReportDetails> {
        TODO("Not yet implemented")
    }

}