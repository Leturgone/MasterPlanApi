package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.GetSubordinatesTaskReportsCommand
import api.masterplan.app.reportsModule.application.ports.ReportEmployeesPort
import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.TaskReportService
import org.springframework.stereotype.Service

@Service
class GetSubordinatesTaskReportsUseCase(
    private val taskReportsService: TaskReportService,
    private val reportEmployeesPort: ReportEmployeesPort
) {
    operator fun invoke(command: GetSubordinatesTaskReportsCommand) : Result<List<TaskReportDetails> > {
        return try {
            val subordinatesIds = reportEmployeesPort.getSubordinates(command.directorId)
            val taskReports = taskReportsService.getSubordinatesTaskReports(subordinatesIds)
            Result.success(taskReports)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}