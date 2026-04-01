package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.ChangeTaskStatusCommand
import api.masterplan.app.plansModule.application.ports.PlanNotificationPort
import api.masterplan.app.plansModule.domain.interfaces.PlanService
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import api.masterplan.app.plansModule.domain.model.value.TaskId
import org.springframework.stereotype.Service


@Service
class ChangeTaskStatusUseCase(
    private val planService: PlanService,
    private val taskService: TaskService,
    private val notificationPort: PlanNotificationPort
) {
    operator fun invoke(command: ChangeTaskStatusCommand): Result<TaskId>{
        return try {
            val updatedStatusTask = taskService.updateTaskStatus(command.taskId,command.status)
            val dirId = planService.getPlanById(updatedStatusTask.planId).directorId
            dirId?.let {
                notificationPort.sendTaskChangeStatusNotification(
                    directorId = dirId,
                    taskTitle = updatedStatusTask.title,
                    taskStatus = updatedStatusTask.status,
                )
            }
            Result.success(updatedStatusTask.id)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}