package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.DeleteTaskFromPlanCommand
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import api.masterplan.app.plansModule.domain.model.value.TaskId
import org.springframework.stereotype.Service

@Service
class DeleteTaskFromPlanUseCase(
    private val taskService: TaskService,
    private val planFilesPort: PlanFilesPort
) {
    operator fun invoke(command: DeleteTaskFromPlanCommand): Result<TaskId> {
        return try {
            val deletedTask = taskService.getTaskById(taskId = command.taskId)
            val deletedTaskId = taskService.deleteTask(deletedTask.id)
            deletedTask.documentId?.let { planFilesPort.removeTaskFile(it)}
            Result.success(deletedTaskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}