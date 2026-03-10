package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.FilterByStatusToCheckPlanReportsCommand
import api.masterplan.app.reportsModule.application.ports.EmployeesPort
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import org.springframework.stereotype.Service

@Service
class FilterByStatusToCheckPlanReportsUseCase(
    private val taskReportService: TaskReportService,
    private val employeesPort: EmployeesPort
) {
    operator fun invoke(command: FilterByStatusToCheckPlanReportsCommand): Result<List<TaskReportDetails>>{
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