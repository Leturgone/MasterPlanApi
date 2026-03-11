package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.GetCreatedTaskReportsCommand
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import org.springframework.stereotype.Service

@Service
class GetCreatedTaskReportsUseCase(
    private val taskReportService: TaskReportService
) {
    operator fun invoke(command: GetCreatedTaskReportsCommand): Result<List<TaskReportDetails>>{
        return try {
            val list = taskReportService.getCreatedTaskReports(command.employeeId)
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}