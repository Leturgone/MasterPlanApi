package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.GetPlanReportInfCommand
import api.masterplan.app.reportsModule.domain.dtos.PlanReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportService
import org.springframework.stereotype.Service

@Service
class GetPlanReportInfUseCase(
    private val planReportService: PlanReportService
) {
    operator fun invoke(command: GetPlanReportInfCommand): Result<PlanReportDetails>{
        return try {
            val planReport = planReportService.getPlanReport(command.planReportId)
            Result.success(planReport)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}