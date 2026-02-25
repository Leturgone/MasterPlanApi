package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.CreatePlanCommand
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.model.value.PlanId
import org.springframework.stereotype.Service


@Service
class CreatePlanUseCase(
    private val planService: PlanService,
    private val planFilesPort: PlanFilesPort
) {
    operator fun invoke(command: CreatePlanCommand): Result<PlanId> {
        return try {
            val planFileId = command.document?.let { planFilesPort.uploadPlanFile(it) }
            val planId = planService.createPlan(
                id = command.id,
                title = command.title,
                description = command.description,
                startDate = command.startDate,
                endDate = command.endDate,
                directorId = command.directorId,
                documentId = planFileId
            )
            Result.success(planId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}