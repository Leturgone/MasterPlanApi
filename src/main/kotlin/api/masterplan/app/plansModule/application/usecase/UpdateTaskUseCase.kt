package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.UpdateTaskCommand
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.application.ports.PlanNotificationPort
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import api.masterplan.app.plansModule.domain.model.value.TaskId
import org.springframework.stereotype.Service


@Service
class UpdateTaskUseCase(
    private val taskService: TaskService,
    private val planFilesPort: PlanFilesPort,
    private val notificationPort: PlanNotificationPort
) {
    operator fun invoke(command: UpdateTaskCommand):Result<TaskId> {
        return try {
            val updatedTask = taskService.updateTask(command.taskId,command.updatedTask)
            command.document?.let {
                val docId = planFilesPort.uploadOrUpdateTaskFile(command.updatedTask.documentId,it)
                taskService.assignTaskDocumentToTask(updatedTask.id,docId)
            }
            notificationPort.sendTaskUpdatedNotification(
                executorsIds = updatedTask.executorsIds,
                taskTitle = updatedTask.title,
            )
            Result.success(updatedTask.id)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}