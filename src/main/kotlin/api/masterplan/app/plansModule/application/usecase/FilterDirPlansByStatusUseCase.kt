package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.FilterDirPlansByStatusCommand
import api.masterplan.app.plansModule.domain.dtos.PlanDetails
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import org.springframework.stereotype.Service

@Service
class FilterDirPlansByStatusUseCase(
    private val planService: PlanService,
) {
   operator fun invoke(command: FilterDirPlansByStatusCommand) : Result<List<PlanDetails>>{
       return try {
           val plans = planService.filterDirPlansByStatus(
               command.directorId,command.status
           )
           Result.success(plans)
       }catch (e: Exception){
           Result.failure(e)
       }
   }
}