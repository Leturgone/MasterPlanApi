package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.ChangePlanStatusCommand
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.model.value.PlanId
import org.springframework.stereotype.Service


@Service
class ChangePlanStatusUseCase(
    private val planService: PlanService
) {
    operator fun invoke(command: ChangePlanStatusCommand): Result<PlanId>{
        return try {
            val updatedStatusPlanId = planService.updatePlanStatus(command.planId,command.status)
            Result.success(updatedStatusPlanId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}