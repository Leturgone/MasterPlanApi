package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.SortDirPlansByEndDateCommand
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import org.springframework.stereotype.Service

@Service
class SortDirPlansByEndDateUseCase(
    private val planService: PlanService
) {
    operator fun invoke(command: SortDirPlansByEndDateCommand): Result<List<PlanDetails>> {
        return try {
            val plans = planService.sortDirPlansByDate(command.directorId)
            Result.success(plans)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}