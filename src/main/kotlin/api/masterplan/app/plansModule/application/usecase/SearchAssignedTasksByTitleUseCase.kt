package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.CreatePlanCommand
import api.masterplan.app.plansModule.application.command.SearchAssignedTasksByTitleCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class SearchAssignedTasksByTitleUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: SearchAssignedTasksByTitleCommand): Result<List<TaskDetails>> {
        return try {
            val tasks = taskService.searchAssignedTasksByTitle(command.executorId,command.query)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}