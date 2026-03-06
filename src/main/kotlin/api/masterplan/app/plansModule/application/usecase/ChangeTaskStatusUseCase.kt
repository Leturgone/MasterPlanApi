package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.ChangeTaskStatusCommand
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import api.masterplan.app.plansModule.domain.model.value.TaskId
import org.springframework.stereotype.Service


@Service
class ChangeTaskStatusUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: ChangeTaskStatusCommand): Result<TaskId>{
        return try {
            val updatedStatusTaskId = taskService.updateTaskStatus(command.taskId,command.status)
            Result.success(updatedStatusTaskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}