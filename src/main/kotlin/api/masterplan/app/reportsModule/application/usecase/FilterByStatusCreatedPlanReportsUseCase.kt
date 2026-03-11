package api.masterplan.app.reportsModule.application.usecase

import api.masterplan.app.reportsModule.application.command.FilterByStatusCreatedPlanReportsCommand
import api.masterplan.app.reportsModule.domain.dtos.PlanReportDetails
import api.masterplan.app.reportsModule.domain.interfaces.PlanReportService
import org.springframework.stereotype.Service

@Service
class FilterByStatusCreatedPlanReportsUseCase(
    private val planReportService: PlanReportService
) {
    operator fun invoke(command: FilterByStatusCreatedPlanReportsCommand): Result<List<PlanReportDetails>>{
        return try {
            val filterList = planReportService.filterCreatedPlanByStatus(
                command.employeeId,command.status
            )
            Result.success(filterList)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}