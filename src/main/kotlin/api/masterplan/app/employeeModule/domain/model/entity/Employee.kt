package api.masterplan.app.employeeModule.domain.model.entity

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname

@ConsistentCopyVisibility
data class Employee private constructor(
    val id: EmployeeId,
    val name: EmployeeName,
    val surname: EmployeeSurname,
    val patronymic: EmployeePatronymic? = null,
    val directorId: EmployeeId? = null
){
    companion object{
        fun create(id: EmployeeId? = null, name: EmployeeName,
                   surname: EmployeeSurname, patronymic: EmployeePatronymic? = null,
                   directorId: EmployeeId? = null): Employee {
            return Employee(
                id = id ?: EmployeeId.generate(),
                name = name,
                surname = surname,
                patronymic = patronymic,
                directorId = directorId
            )
        }
    }
}
