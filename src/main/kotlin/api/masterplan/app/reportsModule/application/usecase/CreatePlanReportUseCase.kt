package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.CreatePlanReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportService
import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import org.springframework.stereotype.Service

@Service
class CreatePlanReportUseCase(
    private val planReportService: PlanReportService,
    private val reportFilesPort: ReportFilesPort
) {
    operator fun invoke(command: CreatePlanReportCommand): Result<PlanReportId> {
        return try {
            val planReportFileId = reportFilesPort.uploadReportFile(command.document)
            val planReportId = planReportService.createPlanReport(
                id = command.id,
                title = command.title,
                description = command.description,
                employeeId = command.employeeId,
                taskId = command.taskId,
                documentId = planReportFileId,
            )
            Result.success(planReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}