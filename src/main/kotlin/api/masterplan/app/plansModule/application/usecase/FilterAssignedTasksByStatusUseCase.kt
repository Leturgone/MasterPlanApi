package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.FilterAssignedTasksByStatusCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class FilterAssignedTasksByStatusUseCase(
    private val taskService: TaskService,
) {
    operator fun invoke(command: FilterAssignedTasksByStatusCommand) : Result<List<TaskDetails>>{
        return try {
            val tasks = taskService.filterAssignedTasksByStatus(command.executorId,command.taskStatus)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}