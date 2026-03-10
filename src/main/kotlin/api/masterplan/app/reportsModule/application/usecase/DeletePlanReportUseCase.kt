package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.DeletePlanReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportService
import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import org.springframework.stereotype.Service

@Service
class DeletePlanReportUseCase(
    private val planReportService: PlanReportService,
    private val reportFilesPort: ReportFilesPort
) {
    operator fun invoke(command: DeletePlanReportCommand): Result<PlanReportId> {
        return try {
            val deletedReport = planReportService.getPlanReport(command.reportId)
            val reportFileId = deletedReport.documentId

            val deletedReportId = planReportService.deletePlanReport(command.reportId)
            reportFilesPort.removeReportFile(reportFileId)

            Result.success(deletedReportId)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}