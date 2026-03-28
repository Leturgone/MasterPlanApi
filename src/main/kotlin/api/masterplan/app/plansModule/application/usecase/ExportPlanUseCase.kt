package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.ExportPlanCommand
import api.masterplan.app.plansModule.application.dto.PlanExportFile
import api.masterplan.app.plansModule.application.ports.ExecutorsPort
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class ExportPlanUseCase(
    private val taskService: TaskService,
    private val planService: PlanService,
    private val executorsPort: ExecutorsPort,
    private val planFilesPort: PlanFilesPort
) {
    operator fun invoke(command: ExportPlanCommand): Result<PlanExportFile> {
        return try {
            val planTitle = planService.getPlanById(command.planId).title
            val planTasks = taskService.getTasksByPlanId(command.planId)
            val executors = executorsPort.getExecutorsForTasks(planTasks)
            val exportFile = planFilesPort.exportPlan(planTitle, planTasks,executors)
            Result.success(exportFile)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}