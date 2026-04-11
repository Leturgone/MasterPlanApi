package api.masterplan.app.employeeModule.application.command

import api.masterplan.app.employeeModule.domain.model.value.EmployeeUserId

data class GetEmployeeByUserIdCommand(
    val id: EmployeeUserId
)