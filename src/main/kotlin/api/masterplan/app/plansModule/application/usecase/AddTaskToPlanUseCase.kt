package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.AddTaskToPlanCommand
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import api.masterplan.app.plansModule.domain.model.value.TaskId
import org.springframework.stereotype.Service

@Service
class AddTaskToPlanUseCase(
    private val taskService: TaskService,
    private val planFilesPort: PlanFilesPort
) {
    operator fun invoke(command: AddTaskToPlanCommand): Result<TaskId> {
        return try {
            val taskFileId = command.document?.let { planFilesPort.uploadTaskFile(it) }
            val taskId = taskService.createTask(
                id = command.taskId,
                title = command.title,
                description = command.description,
                endDate = command.endDate,
                planId = command.planId,
                documentId = taskFileId,
                executorsId = command.executorsId,
            )
            Result.success(taskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}