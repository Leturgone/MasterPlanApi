package api.masterplan.app.reportsModule.application.service

import api.masterplan.app.logging.LoggingMethod
import api.masterplan.app.reportsModule.application.mapper.ReportToEntityMapper
import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.dtos.ReportUpdateData
import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportRepository
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportRepository
import api.masterplan.app.reportsModule.domain.models.entity.Report
import api.masterplan.app.reportsModule.domain.models.value.*
import org.springframework.stereotype.Service

@Service
class ReportServiceImpl(
    private val planReportRepository: PlanReportRepository,
    private val taskReportRepository: TaskReportRepository
): ReportService {

    @LoggingMethod("reportModule")
    override fun getReport(reportId: ReportId, reportType: ReportType): ReportDetails {
        val report =  when(reportType) {
            ReportType.TASK -> planReportRepository.getPlanReport(reportId)
            ReportType.PLAN -> taskReportRepository.getTaskReport(reportId)
        }?: throw ReportException.ReportNotExist(reportId, reportType)
        return ReportToEntityMapper.toReportDetails(report)
    }


    @LoggingMethod("reportModule")
    override fun updateReport(reportId: ReportId, reportType: ReportType, updatedData: ReportUpdateData): ReportId {
        val report =  when(reportType) {
            ReportType.TASK -> planReportRepository.getPlanReport(reportId)
            ReportType.PLAN -> taskReportRepository.getTaskReport(reportId)
        }?: throw ReportException.ReportNotExist(reportId, reportType)

        val updatedReport = report.update(
            title = updatedData.title,
            description = updatedData.description,
            documentId = updatedData.documentId,
        )

        val updatedReportId = when(reportType) {
            ReportType.TASK -> planReportRepository.updatePlanReport(reportId, updatedReport)
            ReportType.PLAN -> taskReportRepository.updateTaskReport(reportId, updatedReport)
        }?: throw ReportException.FailedToUpdateReport(reportId, reportType)

        return updatedReportId
    }


    @LoggingMethod("reportModule")
    override fun deleteReport(reportId: ReportId, reportType: ReportType): ReportId {
        val deletedReportId = when(reportType) {
            ReportType.TASK -> planReportRepository.deletePlanReport(reportId)
            ReportType.PLAN -> taskReportRepository.deleteTaskReport(reportId)
        }?: throw ReportException.FailedToDeleteReport(reportId,reportType)

        return deletedReportId
    }


    @LoggingMethod("reportModule")
    override fun createReport(id: ReportId?, title: ReportTitle, description: ReportDescription?,
        employeeId: ReportEmployeeId, referenceId: ReportReferenceId, documentId: ReportDocumentId): ReportId {

        val reportExist = when(referenceId){
            is ReportReferenceId.ForPlan -> planReportRepository.isPlanReportExist(employeeId,title)
            is ReportReferenceId.ForTask -> taskReportRepository.isTaskReportExist(employeeId,title)
        }

        if (reportExist) throw ReportException.ReportAlreadyExist(employeeId,title)

        val reportEntity = Report.create(
            id = id,
            title = title,
            description = description,
            employeeId = employeeId,
            referenceId = referenceId,
            documentId = documentId
        )

        val reportId = when(reportEntity.type){
            ReportType.TASK ->  taskReportRepository.saveTaskReport(reportEntity)
            ReportType.PLAN -> planReportRepository.savePlanReport(reportEntity)
        }?: throw ReportException.FailedToSaveReport(employeeId,title,reportEntity.type)

        return reportId

    }


    @LoggingMethod("reportModule")
    override fun getCreatedReports(employeeId: ReportEmployeeId, reportType: ReportType): List<ReportDetails> {
        val reportList = when(reportType) {
            ReportType.TASK -> taskReportRepository.getTaskReportsByEmployeeId(employeeId)
            ReportType.PLAN -> planReportRepository.getPlanReportsByEmployeeId(employeeId)
        }

        return reportList.map { ReportToEntityMapper.toReportDetails(it) }
    }


    @LoggingMethod("reportModule")
    override fun filterCreatedReportsByStatus(employeeId: ReportEmployeeId, reportType: ReportType, status: ReportStatus): List<ReportDetails> {

        val reportList = when(reportType) {
            ReportType.TASK -> taskReportRepository.getTaskReportsByEmployeeId(employeeId)
            ReportType.PLAN -> planReportRepository.getPlanReportsByEmployeeId(employeeId)
        }

        return reportList.filter { planReport ->
            planReport.reportStatus == status
        }.map {
            ReportToEntityMapper.toReportDetails(it)
        }
    }


    @LoggingMethod("reportModule")
    override fun changeReportStatus(reportId: ReportId, reportType: ReportType, status: ReportStatus): ReportId {
        val report =  when(reportType) {
            ReportType.TASK -> planReportRepository.getPlanReport(reportId)
            ReportType.PLAN -> taskReportRepository.getTaskReport(reportId)
        }?: throw ReportException.ReportNotExist(reportId, reportType)

        val reportWithNewStatus = report.changeReportStatus(status)

        val updatedReportId = when(reportType) {
            ReportType.TASK -> planReportRepository.updatePlanReport(reportId, reportWithNewStatus)
            ReportType.PLAN -> taskReportRepository.updateTaskReport(reportId, reportWithNewStatus)
        }?: throw ReportException.FailedToUpdateReportStatus(status,reportId, reportType)

        return updatedReportId

    }


    @LoggingMethod("reportModule")
    override fun getSubordinatesTaskReports(subordinatesIds: Set<ReportEmployeeId>): List<ReportDetails> {
        val reportList = taskReportRepository.getTaskReportByEmployeeIds(subordinatesIds)

        return reportList.map { ReportToEntityMapper.toReportDetails(it) }
    }


    @LoggingMethod("reportModule")
    override fun filterSubordinatesTaskReportsByStatus(subordinatesIds: Set<ReportEmployeeId>,
                                                       status: ReportStatus): List<ReportDetails> {
        val reportList = taskReportRepository.getTaskReportByEmployeeIds(subordinatesIds)

        return reportList.filter { taskReport ->
            taskReport.reportStatus == status
        }.map {
            ReportToEntityMapper.toReportDetails(it)
        }
    }

}