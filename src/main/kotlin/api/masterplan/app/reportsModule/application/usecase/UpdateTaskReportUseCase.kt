package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.UpdateTaskReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import org.springframework.stereotype.Service

@Service
class UpdateTaskReportUseCase(
    private val taskReportService: TaskReportService,
    private val reportFilesPort: ReportFilesPort
) {
    operator fun invoke(command: UpdateTaskReportCommand):Result<TaskReportId>{
        return try {
            val updatedReport = taskReportService.updateTaskReport(
                reportId = command.reportId,
                updatedTaskReport = command.updatedReport
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