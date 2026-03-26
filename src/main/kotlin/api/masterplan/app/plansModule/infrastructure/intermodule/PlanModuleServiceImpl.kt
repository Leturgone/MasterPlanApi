package api.masterplan.app.plansModule.infrastructure.intermodule

import api.masterplan.app.apiContracts.plans.PlanModuleService
import api.masterplan.app.apiContracts.plans.TaskModelDto
import api.masterplan.app.plansModule.application.command.GetAssignedTasksCommand
import api.masterplan.app.plansModule.application.command.GetAssignedTasksForMultipleEmpCommand
import api.masterplan.app.plansModule.application.usecase.GetAssignedTasksForMultipleEmpUseCase
import api.masterplan.app.plansModule.application.usecase.GetAssignedTasksUseCase
import api.masterplan.app.plansModule.domain.model.value.ExecutorId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PlanModuleServiceImpl(
    private val getAssignedTasksUseCase: GetAssignedTasksUseCase,
    private val getAllDirEmpTasksUseCase: GetAssignedTasksForMultipleEmpUseCase
): PlanModuleService {
    override fun getTasksByEmployeeId(employeeId: UUID): Result<List<TaskModelDto>> {
        return try {
            val command = GetAssignedTasksCommand(
                executorId = ExecutorId(employeeId)
            )
            val result = getAssignedTasksUseCase(command).getOrThrow()
            val mappedResult = InterModuleTaskToDtoSuccessMapper.toDto(result)
            Result.success(mappedResult)
        }catch (e: Exception){
            val exception = InterModuleTaskToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }

    override fun getTasksByEmployeeIds(employeeIds: Set<UUID>): Result<List<TaskModelDto>> {
        return try {
            val command = GetAssignedTasksForMultipleEmpCommand(
            executorIds = employeeIds.map { ExecutorId(it) }.toSet()
            )
            val result = getAllDirEmpTasksUseCase(command).getOrThrow()
            val mappedResult = InterModuleTaskToDtoSuccessMapper.toDto(result)
            Result.success(mappedResult)
        }catch (e: Exception){
            val exception = InterModuleTaskToDtoErrorMapper.toDto(e)
            Result.failure(exception)
        }
    }
}