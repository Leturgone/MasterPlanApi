package api.masterplan.app.employeeModule.domain.dtos

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics
import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname

data class EmployeeWithMetricsDetails(
    val id: EmployeeId,
    val name: EmployeeName,
    val surname: EmployeeSurname,
    val patronymic: EmployeePatronymic? = null,
    val director: DirectorDetails? = null,
    val metrics: EmployeeMetrics,
)
