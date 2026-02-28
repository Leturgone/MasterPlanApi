package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.GetPlanInfCommand
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import org.springframework.stereotype.Service

@Service
class GetPlanInfUseCase(
    private val planService: PlanService
) {
    operator fun invoke(command: GetPlanInfCommand): Result<PlanDetails> {
        return try {
            val planDetails = planService.getPlanById(command.planId)
            Result.success(planDetails)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}