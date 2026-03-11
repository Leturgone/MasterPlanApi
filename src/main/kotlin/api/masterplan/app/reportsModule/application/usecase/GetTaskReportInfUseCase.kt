package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.GetTaskReportInfCommand
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import org.springframework.stereotype.Service

@Service
class GetTaskReportInfUseCase(
    private val taskReportService: TaskReportService
) {
    operator fun invoke(command: GetTaskReportInfCommand): Result<TaskReportDetails> {
        return try {
            val taskReport = taskReportService.getTaskReport(command.taskReportId)
            Result.success(taskReport)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}