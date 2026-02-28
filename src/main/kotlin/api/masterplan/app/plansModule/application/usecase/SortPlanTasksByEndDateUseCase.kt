package api.masterplan.app.plansModule.application.usecase

import api.masterplan.app.plansModule.application.command.SortPlanTasksByEndDateCommand
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import api.masterplan.app.plansModule.domain.interfaces.TaskService
import org.springframework.stereotype.Service

@Service
class SortPlanTasksByEndDateUseCase(
    private val taskService: TaskService
) {
    operator fun invoke(command: SortPlanTasksByEndDateCommand): Result<List<TaskDetails>> {
        return try {
            val tasks = taskService.sortPlansTasksByDate(command.planId)
            Result.success(tasks)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}