package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.ChangePlanReportStatusCommand
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportService
import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import org.springframework.stereotype.Service


@Service
class ChangePlanReportStatusUseCase(
    private val planReportService: PlanReportService
) {
    operator fun invoke(command: ChangePlanReportStatusCommand): Result<PlanReportId> {
        return try {
            val planReportId = planReportService.changePlanReportStatus(
                command.reportId,command.status
            )
            Result.success(planReportId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}