package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.ChangeReportStatusCommand
import api.masterplan.app.reportsModule.domain.interfaces.ReportService
import api.masterplan.app.reportsModule.domain.models.value.ReportId
import org.springframework.stereotype.Service

@Service
class ChangeReportStatusUseCase(
    private val planReportService: ReportService
) {
    operator fun invoke(command: ChangeReportStatusCommand): Result<ReportId> {
        return try {
            val planReportId = planReportService.changeReportStatus(
                reportId = command.reportId,
                reportType = command.reportType,
                status = command.status
            )
            Result.success(planReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}