package api.masterplan.app.employeeModule.infrastructure.adapters

import api.masterplan.app.employeeModule.application.dto.EmpTaskModel
import api.masterplan.app.employeeModule.application.dto.EmpTaskStatus
import api.masterplan.app.employeeModule.application.exceptions.EmpTaskException
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.plansModule.TaskModelDto

internal object EmployeeInnerModuleSuccessMapper {
    fun toEmpTaskModelList(tasks: List<TaskModelDto>): List<EmpTaskModel> {
        return tasks.map { taskModelDto ->
            val employees = taskModelDto.employeeIds.map { employeeId -> EmployeeId(employeeId) }
            val status = try {
                EmpTaskStatus.valueOf(taskModelDto.status)
            }catch (_:IllegalArgumentException){
                throw EmpTaskException.InvalidStatusException(taskModelDto.status)
            }
            EmpTaskModel(
                employeeIds = employees,
                status = status,
                weight = taskModelDto.weight,
            )
        }
    }
}