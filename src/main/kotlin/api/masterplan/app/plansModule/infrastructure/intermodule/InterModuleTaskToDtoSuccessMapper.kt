package api.masterplan.app.plansModule.infrastructure.intermodule

import api.masterplan.app.plansModule.TaskModelDto
import api.masterplan.app.plansModule.domain.dtos.TaskDetails

internal object InterModuleTaskToDtoSuccessMapper {
    fun toDto(tasks:List<TaskDetails>): List<TaskModelDto>{
        return tasks.map { taskDetails ->
            TaskModelDto(
                employeeIds = taskDetails.executorsIds.map { it.value },
                status = taskDetails.status.name,
                weight = taskDetails.urgency.value
            )
        }
    }
}