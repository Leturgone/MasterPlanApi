package api.masterplan.app.employeeModule.domain.dtos

import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname

data class DirectorDetails(
    val name: EmployeeName,
    val surname: EmployeeSurname,
    val patronymic: EmployeePatronymic? = null,
)