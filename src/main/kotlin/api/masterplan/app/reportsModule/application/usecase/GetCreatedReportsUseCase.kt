package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.GetCreatedReportsCommand
import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import org.springframework.stereotype.Service

@Service
class GetCreatedReportsUseCase(
    private val reportService: ReportService
) {
    operator fun invoke(command: GetCreatedReportsCommand): Result<List<ReportDetails>>{
        return try {
            val plans = reportService.getCreatedReports(command.employeeId,command.reportType)
            Result.success(plans)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}