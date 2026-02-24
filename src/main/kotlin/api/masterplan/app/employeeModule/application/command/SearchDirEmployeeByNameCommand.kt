package api.masterplan.app.employeeModule.application.command

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId

data class SearchDirEmployeeByNameCommand(
    val query: String,
    val directorId: EmployeeId
)