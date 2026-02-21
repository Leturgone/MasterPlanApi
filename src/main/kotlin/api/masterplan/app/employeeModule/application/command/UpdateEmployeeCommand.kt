package api.masterplan.app.employeeModule.application.command

import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId

data class UpdateEmployeeCommand(
    val id: EmployeeId,
    val newEmployee: Employee
)