package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.FilterByStatusToSubordinatesTaskReportsCommand
import api.masterplan.app.reportsModule.application.ports.ReportEmployeesPort
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import org.springframework.stereotype.Service

@Service
class FilterByStatusSubordinatesTaskReportsUseCase(
    private val taskReportService: TaskReportService,
    private val reportEmployeesPort: ReportEmployeesPort
) {
    operator fun invoke(command: FilterByStatusToSubordinatesTaskReportsCommand): Result<List<TaskReportDetails>>{
        return try {
            val subordinatesList = reportEmployeesPort.getSubordinates(command.directorId)
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