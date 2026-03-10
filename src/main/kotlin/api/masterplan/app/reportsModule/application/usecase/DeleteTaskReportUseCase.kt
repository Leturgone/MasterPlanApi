package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.DeleteTaskReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import org.springframework.stereotype.Service

@Service
class DeleteTaskReportUseCase(
    private val taskReportService: TaskReportService,
    private val reportFilesPort: ReportFilesPort
) {
    operator fun invoke(command: DeleteTaskReportCommand): Result<TaskReportId> {
        return try {
            val deletedReport = taskReportService.getTaskReport(command.reportId)
            val reportFileId = deletedReport.documentId

            val deletedReportId = taskReportService.deleteTaskReport(command.reportId)
            reportFilesPort.removeReportFile(reportFileId)

            Result.success(deletedReportId)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}