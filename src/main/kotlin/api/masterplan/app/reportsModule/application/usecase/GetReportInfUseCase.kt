package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.GetReportInfCommand
import api.masterplan.app.reportsModule.domain.dtos.ReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import org.springframework.stereotype.Service

@Service
class GetReportInfUseCase(
    private val reportService: ReportService
) {
    operator fun invoke(command: GetReportInfCommand): Result<ReportDetails>{
        return try {
            val planReport = reportService.getReport(command.reportId,command.reportType)
            Result.success(planReport)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}