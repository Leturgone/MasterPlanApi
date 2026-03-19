package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.FilterByStatusToSubordinatesTaskReportsCommand
import api.masterplan.app.reportsModule.application.ports.ReportEmployeesPort
import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import org.springframework.stereotype.Service

@Service
class FilterByStatusSubordinatesTaskReportsUseCase(
    private val reportService: ReportService,
    private val reportEmployeesPort: ReportEmployeesPort
) {
    operator fun invoke(command: FilterByStatusToSubordinatesTaskReportsCommand): Result<List<ReportDetails>>{
        return try {
            val subordinatesList = reportEmployeesPort.getSubordinates(command.directorId)
            val filterList = reportService.filterSubordinatesTaskReportsByStatus(
                subordinatesIds = subordinatesList,
                status = command.status
            )
            Result.success(filterList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}