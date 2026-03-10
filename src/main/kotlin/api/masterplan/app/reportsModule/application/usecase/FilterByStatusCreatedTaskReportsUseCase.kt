package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.FilterByStatusCreatedTaskReportsCommand
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import org.springframework.stereotype.Service

@Service
class FilterByStatusCreatedTaskReportsUseCase(
    private val taskReportService: TaskReportService
) {
    operator fun invoke(command: FilterByStatusCreatedTaskReportsCommand): Result<List<TaskReportDetails>>{
        return try {
            val filterList = taskReportService.filterCreatedTaskByStatus(
                employeeId = command.employeeId,
                status = command.status
            )
            Result.success(filterList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}