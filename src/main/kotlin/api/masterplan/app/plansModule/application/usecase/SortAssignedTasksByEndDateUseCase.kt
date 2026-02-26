package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.SortAssignedTasksByEndDateCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class SortAssignedTasksByEndDateUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: SortAssignedTasksByEndDateCommand): Result<List<TaskDetails>> {
        return try {
            val tasks = taskService.sortAssignedTasksByDate(command.executorId)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}