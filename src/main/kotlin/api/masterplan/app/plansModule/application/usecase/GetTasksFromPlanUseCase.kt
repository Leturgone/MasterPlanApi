package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.GetTasksFromPlanCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class GetTasksFromPlanUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: GetTasksFromPlanCommand): Result<List<TaskDetails>>{
        return try {
            val tasks = taskService.getTasksByPlanId(command.planId)
            Result.success(tasks)
        }catch (e: Exception){
        Result.failure(e)
        }
    }
}