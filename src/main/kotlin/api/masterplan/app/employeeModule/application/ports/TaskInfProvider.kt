package api.masterplan.app.employeeModule.application.ports

import api.masterplan.app.employeeModule.application.dto.EmpTaskModel
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId

interface TaskInfProvider {

    fun getTasksByEmployeeId(employeeId: EmployeeId): List<EmpTaskModel>

    fun getTasksByEmployeeIds(employeeIds: Set<EmployeeId>): List<EmpTaskModel>
}