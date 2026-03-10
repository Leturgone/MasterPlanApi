package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.UpdatePlanReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportService
import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import org.springframework.stereotype.Service

@Service
class UpdatePlanReportUseCase(
    private val planReportService: PlanReportService,
    private val reportFilesPort: ReportFilesPort
) {
    operator fun invoke(command: UpdatePlanReportCommand):Result<PlanReportId>{
        return try {
            val updatedReport = planReportService.updatePlanReport(
                reportId = command.reportId,
                updatedPlanReport = command.updatedReport
            )

            reportFilesPort.updateReportFile(
                reportFileId = command.updatedReport.documentId,
                reportFile = command.document
            )
            Result.success(updatedReport)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}