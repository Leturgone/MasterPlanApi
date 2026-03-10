package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.CreateTaskReportCommand
import api.masterplan.app.reportsModule.application.ports.ReportFilesPort
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import org.springframework.stereotype.Service

@Service
class CreateTaskReportUseCase(
    private val taskReportService: TaskReportService,
    private val reportFilesPort: ReportFilesPort
) {
    operator fun invoke(command: CreateTaskReportCommand): Result<TaskReportId>{
        return try {
            val taskFileId = reportFilesPort.uploadReportFile(command.document)
            val taskReportId = taskReportService.createTaskReport(
                id = command.id,
                title = command.title,
                description = command.description,
                employeeId = command.employeeId,
                taskId = command.taskId,
                documentId = taskFileId,
            )
            Result.success(taskReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}