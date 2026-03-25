package api.masterplan.app.plansModule.infrastructure.adapters

import api.masterplan.app.employeeModule.EmployeeModuleService
import api.masterplan.app.plansModule.application.dto.ExecutorDto
import api.masterplan.app.plansModule.application.ports.ExecutorsPort
import api.masterplan.app.plansModule.domain.dtos.TaskDetails
import org.springframework.stereotype.Component

@Component
class ExecutorsAdapter(
    private val employeeModuleService: EmployeeModuleService
): ExecutorsPort {
    override fun getExecutorsForTasks(tasks: List<TaskDetails>): List<ExecutorDto> {
        val executors = tasks.flatMap { it.executorsIds }.toSet()
        val executorsDataList = executors.map {
            val emp = employeeModuleService.getEmployeeById(it.value).getOrElse {ex ->
                throw PlansInnerModuleErrorMapper.exceptionToModuleException(ex)
            }
            PlansInnerModuleSuccessMapper.toExecutorDto(
                executorId = it.value,
                executor = emp
            )
        }
        return executorsDataList
    }

}