package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.ExportPlanCommand
import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import org.springframework.stereotype.Service

@Service
class ExportPlanUseCase(
    private val planService: PlanService,
    private val planFilesPort: PlanFilesPort
) {
    operator fun invoke(command: ExportPlanCommand): Result<PlanExportFile> {
        return try {
            val plan = planService.getPlanById(command.planId)
            val exportFile = planFilesPort.exportPlan(plan)
            Result.success(exportFile)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}