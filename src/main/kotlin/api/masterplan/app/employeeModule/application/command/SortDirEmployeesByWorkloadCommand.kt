package api.masterplan.app.employeeModule.application.command

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId

data class SortDirEmployeesByWorkloadCommand(
    val directorId: EmployeeId
)