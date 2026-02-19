package api.masterplan.app.employeeModule.application.dto

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId

data class EmpTaskModel(
    val employeeId: EmployeeId,
    val status: EmpTaskStatus,
    val weight: Double,
)
