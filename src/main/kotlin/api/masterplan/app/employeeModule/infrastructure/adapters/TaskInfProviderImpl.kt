package api.masterplan.app.employeeModule.infrastructure.adapters

import api.masterplan.app.employeeModule.application.dto.EmpTaskModel
import api.masterplan.app.employeeModule.application.ports.TaskInfProvider
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import org.springframework.stereotype.Component

@Component
class TaskInfProviderImpl(): TaskInfProvider {
    override fun getTasksByEmployeeId(employeeId: EmployeeId): List<EmpTaskModel> {
        println("getTasksByEmployeeId = $employeeId")
        TODO("Not yet implemented")
    }

    override fun getTasksByEmployeeIds(employeeIds: Set<EmployeeId>): List<EmpTaskModel> {
        println("getTasksByEmployeeIds = $employeeIds")
        TODO("Not yet implemented")
    }
}