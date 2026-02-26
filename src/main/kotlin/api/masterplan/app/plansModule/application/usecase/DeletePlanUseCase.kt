package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.DeletePlanCommand
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.model.value.PlanId
import org.springframework.stereotype.Service

@Service
class DeletePlanUseCase(
    private val planService: PlanService,
    private val planFilesPort: PlanFilesPort
) {
    operator fun invoke(command: DeletePlanCommand): Result<PlanId>{
        return try {

            val deletedPlan = planService.getPlanById(command.planId)
            val planFileId = deletedPlan.documentId
            val tasks = deletedPlan.tasks
            val deletedPlanId = planService.deletePlan(command.planId)

            // Удаление файлов
            tasks.forEach { task -> task.documentId?.let { planFilesPort.removeTaskFile(it) } }
            planFileId?.let { planFilesPort.removePlanFile(it) }

            Result.success(deletedPlanId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}