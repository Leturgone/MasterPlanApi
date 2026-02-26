package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.UpdatePlanCommand
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.model.value.PlanId
import org.springframework.stereotype.Service

@Service
class UpdatePlanUseCase(
    private val planService: PlanService
) {
    operator fun invoke(command: UpdatePlanCommand): Result<PlanId> {
        return try {
            val updatedPlanId = planService.updatePlan(command.planId, command.updatedPlan)
            Result.success(updatedPlanId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}