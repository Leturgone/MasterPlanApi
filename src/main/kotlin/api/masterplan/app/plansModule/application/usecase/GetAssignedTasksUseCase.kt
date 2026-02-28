package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.GetAssignedTasksCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class GetAssignedTasksUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: GetAssignedTasksCommand): Result<List<TaskDetails>>{
        return try {
            val tasks = taskService.getAssignedTasks(command.executorId)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}