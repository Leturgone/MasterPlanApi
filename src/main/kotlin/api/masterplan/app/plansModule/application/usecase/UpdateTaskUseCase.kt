package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.UpdateTaskCommand
import api.masterplan.app.plansModule.application.ports.PlanFilesPort
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import api.masterplan.app.plansModule.domain.model.value.TaskId
import org.springframework.stereotype.Service


@Service
class UpdateTaskUseCase(
    private val taskService: TaskService,
    private val planFilesPort: PlanFilesPort
) {
    operator fun invoke(command: UpdateTaskCommand):Result<TaskId> {
        return try {
            val taskDocId = taskService.getTaskById(command.taskId).documentId

            val docId = command.document?.let {planFilesPort.uploadOrUpdateTaskFile(taskDocId,it)}

            val updatedTask  = docId?.let { command.updatedTask.addDocument(it) }?: command.updatedTask

            val updatedTaskId = taskService.updateTask(command.taskId,updatedTask)

            Result.success(updatedTaskId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}