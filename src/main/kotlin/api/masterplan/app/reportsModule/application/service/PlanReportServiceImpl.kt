package api.masterplan.app.reportsModule.application.service

import api.masterplan.app.logging.LoggingMethod
import api.masterplan.app.reportsModule.application.mapper.ReportToEntityMapper
import api.masterplan.app.reportsModule.domain.dtos.PlanReportDetails
import api.masterplan.app.reportsModule.domain.exceptions.ReportException
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportRepository
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportService
import api.masterplan.app.reportsModule.domain.models.entity.PlanReport
import api.masterplan.app.reportsModule.domain.models.value.PlanReportDescription
import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import api.masterplan.app.reportsModule.domain.models.value.PlanReportStatus
import api.masterplan.app.reportsModule.domain.models.value.PlanReportTitle
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportTaskId
import org.springframework.stereotype.Service

@Service
class PlanReportServiceImpl(
    private val planReportRepository: PlanReportRepository
): PlanReportService {

    @LoggingMethod("reportModule")
    override fun getPlanReport(reportId: PlanReportId): PlanReportDetails {
        val report = planReportRepository.getPlanReport(reportId)
            ?: throw ReportException.PlanReportNotExist(reportId)

        return ReportToEntityMapper.toPlanReportDetails(report)
    }

    @LoggingMethod("reportModule")
    override fun updatePlanReport(reportId: PlanReportId, updatedPlanReport: PlanReport): PlanReportId {
        val report = planReportRepository.getPlanReport(reportId)?: throw ReportException.PlanReportNotExist(reportId)

        val updatedReport = report.update(
            title = updatedPlanReport.title,
            description = updatedPlanReport.description,
            documentId = updatedPlanReport.documentId,
        )
        val updatedReportId = planReportRepository.updatePlanReport(reportId, updatedReport)
            ?: throw ReportException.FailedToUpdatePlanReport(reportId)

        return updatedReportId
    }

    @LoggingMethod("reportModule")
    override fun deletePlanReport(reportId: PlanReportId): PlanReportId {
        val deletedReportId = planReportRepository.deletePlanReport(reportId)
            ?: throw ReportException.FailedToDeletePlanReport(reportId)

        return deletedReportId
    }

    @LoggingMethod("reportModule")
    override fun createPlanReport(id: PlanReportId?, title: PlanReportTitle, description: PlanReportDescription?,
        employeeId: ReportEmployeeId, taskId: ReportTaskId, documentId: ReportDocumentId
    ): PlanReportId {
        if (planReportRepository.isPlanReportExist(employeeId,title)) throw ReportException.PlanReportAlreadyExist(employeeId,title)

        val planReportEntity = PlanReport.create(
            id = id,
            title = title,
            description = description,
            employeeId = employeeId,
            taskId = taskId,
            documentId = documentId,
        )

        val planReportId = planReportRepository.savePlanReport(planReportEntity)
            ?: throw ReportException.FailedToSavePlanReport(employeeId,title)

        return planReportId
    }

    @LoggingMethod("reportModule")
    override fun getCreatedPlanReports(employeeId: ReportEmployeeId): List<PlanReportDetails> {
        val planReportList = planReportRepository.getPlanReportsByEmployeeId(employeeId)

        return planReportList.map { ReportToEntityMapper.toPlanReportDetails(it) }
    }

    @LoggingMethod("reportModule")
    override fun filterCreatedPlanByStatus(employeeId: ReportEmployeeId, status: PlanReportStatus): List<PlanReportDetails> {
        val planReportList = planReportRepository.getPlanReportsByEmployeeId(employeeId)

        return planReportList.filter { planReport ->
            planReport.reportStatus == status
        }.map {
            ReportToEntityMapper.toPlanReportDetails(it)
        }
    }

    @LoggingMethod("reportModule")
    override fun changePlanReportStatus(reportId: PlanReportId, status: PlanReportStatus): PlanReportId {
        val report = planReportRepository.getPlanReport(reportId)?: throw ReportException.PlanReportNotExist(reportId)

        val reportWithNewStatus = report.changePlanReportStatus(status)

        val updatedReportId = planReportRepository.updatePlanReport(reportId, reportWithNewStatus)
            ?: throw ReportException.FailedToUpdatePlanReport(reportId)

        return updatedReportId
    }
}