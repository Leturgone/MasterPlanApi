package api.masterplan.app.employeeModule.infrastructure.adapters

import api.masterplan.app.employeeModule.application.dto.EmpTaskModel
import api.masterplan.app.employeeModule.application.ports.TaskInfProvider
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.plansModule.PlanModuleService
import org.springframework.stereotype.Component

@Component
class TaskInfProviderImpl(
    private val planModuleService: PlanModuleService
): TaskInfProvider {
    override fun getTasksByEmployeeId(employeeId: EmployeeId): List<EmpTaskModel> {
        val taskList = planModuleService.getTasksByEmployeeId(employeeId.value).getOrElse{
            throw EmployeeInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        val taskModelList = EmployeeInnerModuleSuccessMapper.toEmpTaskModelList(taskList)
        return taskModelList
    }

    override fun getTasksByEmployeeIds(employeeIds: Set<EmployeeId>): List<EmpTaskModel> {
        val employeeIdsValue = employeeIds.map { it.value }.toSet()
        val taskList = planModuleService.getTasksByEmployeeIds(employeeIdsValue).getOrElse{
            throw EmployeeInnerModuleErrorMapper.exceptionToModuleException(it)
        }
        val taskModelList = EmployeeInnerModuleSuccessMapper.toEmpTaskModelList(taskList)
        return taskModelList
    }
}