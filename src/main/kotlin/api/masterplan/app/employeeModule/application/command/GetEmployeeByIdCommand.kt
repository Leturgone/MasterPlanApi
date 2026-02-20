package api.masterplan.app.employeeModule.application.command

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId

class GetEmployeeByIdCommand(
    val id: EmployeeId
)