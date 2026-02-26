package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.UpdateTaskCommand
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import api.masterplan.app.plansModule.domain.model.value.TaskId
import org.springframework.stereotype.Service


@Service
class UpdateTaskUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: UpdateTaskCommand):Result<TaskId> {
        return try {
            val updatedTaskId = taskService.updateTask(command.taskId,command.updatedTask)
            Result.success(updatedTaskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}