package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.GetCreatedPlanReportsCommand
import api.masterplan.app.reportsModule.domain.dtos.PlanReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportService
import org.springframework.stereotype.Service

@Service
class GetCreatedPlanReportsUseCase(
    private val planReportService: PlanReportService
) {
    operator fun invoke(command: GetCreatedPlanReportsCommand): Result<List<PlanReportDetails>>{
        return try {
            val plans = planReportService.getCreatedPlanReports(command.employeeId)
            Result.success(plans)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}