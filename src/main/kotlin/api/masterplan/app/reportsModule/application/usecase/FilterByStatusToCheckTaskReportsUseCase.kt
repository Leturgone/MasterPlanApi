package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.FilterByStatusToCheckTaskReportsCommand
import api.masterplan.app.reportsModule.application.ports.EmployeesPort
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import org.springframework.stereotype.Service

@Service
class FilterByStatusToCheckTaskReportsUseCase(
    private val taskReportService: TaskReportService,
    private val employeesPort: EmployeesPort
) {
    operator fun invoke(command: FilterByStatusToCheckTaskReportsCommand): Result<List<TaskReportDetails>>{
        return try {
            val subordinatesList = employeesPort.getSubordinates(command.directorId)
            val filterList = taskReportService.filterSubordinatesTaskReportsByStatus(
                subordinatesIds = subordinatesList,
                status = command.status
            )
            Result.success(filterList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}