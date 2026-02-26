package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.GetDirPlansCommand
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import org.springframework.stereotype.Service

@Service
class GetDirPlansUseCase(
    private val planService: PlanService
) {
    operator fun invoke(command: GetDirPlansCommand): Result<List<PlanDetails>>{
        return try {
            val plans = planService.getAllDirPlans(command.directorId)
            Result.success(plans)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}