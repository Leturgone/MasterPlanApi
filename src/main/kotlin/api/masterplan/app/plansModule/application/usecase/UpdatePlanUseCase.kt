package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.UpdatePlanCommand
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.model.value.PlanId
import org.springframework.stereotype.Service

@Service
class UpdatePlanUseCase(
    private val planService: PlanService,
    private val planFilesPort: PlanFilesPort
) {
    operator fun invoke(command: UpdatePlanCommand): Result<PlanId> {
        return try {
            val planDocId = planService.getPlanById(command.planId).documentId

            val docId = command.document?.let { planFilesPort.uploadOrUpdatePlanFile(planDocId,it) }

            val updatedPlan = docId?.let { command.updatedPlan.addDocument(it)}?: command.updatedPlan

            val updatedPlanId = planService.updatePlan(command.planId, updatedPlan)

            Result.success(updatedPlanId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}