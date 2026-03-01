package api.masterplan.app.employeeModule.application.dto

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId

data class EmpTaskModel(
    val employeeIds: List<EmployeeId>,
    val status: EmpTaskStatus,
    val weight: Double,
)
