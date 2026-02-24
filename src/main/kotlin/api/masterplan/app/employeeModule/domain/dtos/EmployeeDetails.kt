package api.masterplan.app.employeeModule.domain.dtos

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname
import api.masterplan.app.employeeModule.domain.model.value.EmployeeUserId

data class EmployeeDetails(
    val id: EmployeeId,
    val name: EmployeeName,
    val surname: EmployeeSurname,
    val patronymic: EmployeePatronymic? = null,
    val directorId: EmployeeId? = null,
    val userId: EmployeeUserId,
)
