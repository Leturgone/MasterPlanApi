package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.GetSubordinatesTaskReportsCommand
import api.masterplan.app.reportsModule.application.ports.ReportEmployeesPort
import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import org.springframework.stereotype.Service

@Service
class GetSubordinatesTaskReportsUseCase(
    private val reportsService: ReportService,
    private val reportEmployeesPort: ReportEmployeesPort
) {
    operator fun invoke(command: GetSubordinatesTaskReportsCommand) : Result<List<ReportDetails> > {
        return try {
            val subordinatesIds = reportEmployeesPort.getSubordinates(command.directorId)
            val taskReports = reportsService.getSubordinatesTaskReports(subordinatesIds)
            Result.success(taskReports)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}