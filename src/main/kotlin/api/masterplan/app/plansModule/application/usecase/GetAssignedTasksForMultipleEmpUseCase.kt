package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.GetAssignedTasksForMultipleEmpCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

// Для межмодульного использования
@Service
class GetAssignedTasksForMultipleEmpUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: GetAssignedTasksForMultipleEmpCommand): Result<List<TaskDetails>>{
        return try {
            val tasks = taskService.getAssignedTasksForMultipleExecutors(command.executorIds)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}