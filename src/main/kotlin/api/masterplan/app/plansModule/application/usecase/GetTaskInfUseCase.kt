package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.GetTaskInfCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class GetTaskInfUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: GetTaskInfCommand): Result<TaskDetails> {
        return try {
            val task = taskService.getTaskById(command.taskId)
            Result.success(task)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}