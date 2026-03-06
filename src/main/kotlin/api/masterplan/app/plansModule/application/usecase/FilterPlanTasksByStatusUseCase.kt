package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.FilterPlanTasksByStatusCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class FilterPlanTasksByStatusUseCase(
    private val taskService: TaskService,
) {
    operator fun invoke(command: FilterPlanTasksByStatusCommand): Result<List<TaskDetails>>{
        return try {
            val plans = taskService.filterPlanTasksByStatus(command.planId,command.taskStatus)
            Result.success(plans)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}