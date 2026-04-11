package api.masterplan.app.employeeModule.domain.exceptions

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname
import api.masterplan.app.employeeModule.domain.model.value.EmployeeUserId

sealed class EmployeeException(message: String): Exception(message) {

    class InvalidEmployeeName(message: String?): EmployeeException(
        "Invalid employee name: ${message?.let {": $it"  }}"
    )

    class InvalidEmployeeSurname(message: String?): EmployeeException(
        "Invalid employee surname: ${message?.let {": $it"  }}"
    )

    class InvalidEmployeePatronymic(message: String?): EmployeeException(
        "Invalid employee patronymic: ${message?.let {": $it"  }}"
    )

    class EmployeeNotExist(val id: EmployeeId) : EmployeeException(
        "Employee with id = ${id.value} not found"
    )

    class EmployeeNotExistWithUserId(val id: EmployeeUserId) : EmployeeException(
        "Employee with user id = ${id.value} not found"
    )

    class EmployeeAlreadyExists(val name: EmployeeName,val surname: EmployeeSurname,
                                         val patronymic: EmployeePatronymic? = null): EmployeeException(
        "Employee with name $name $surname ${patronymic ?: ""} already exists"
    )

    class FailedToCreateEmployee(val name: EmployeeName,val surname: EmployeeSurname,
                                 val patronymic: EmployeePatronymic? = null): EmployeeException(
        "Failed to create employee with name $name $surname ${patronymic ?: ""}"
    )

    class FailedToUpdateEmployee(val id: EmployeeId): EmployeeException(
        "Failed to update employee with id = $id"
    )

    class FailedToGetDirectorDetailsForEmployee(val id: EmployeeId): EmployeeException(
        "Failed to get director details for employee with id = $id"
    )

    class InternalServerError(message: String) : EmployeeException(message)

}