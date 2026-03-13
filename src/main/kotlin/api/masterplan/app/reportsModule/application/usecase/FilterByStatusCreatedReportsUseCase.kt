package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.FilterByStatusCreatedReportsCommand
import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import org.springframework.stereotype.Service

@Service
class FilterByStatusCreatedReportsUseCase(
    private val reportService: ReportService
) {
    operator fun invoke(command: FilterByStatusCreatedReportsCommand): Result<List<ReportDetails>>{
        return try {
            val filterList = reportService.filterCreatedReportsByStatus(
                employeeId = command.employeeId,
                reportType = command.reportType,
                status = command.status
            )
            Result.success(filterList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}