package api.masterplan.app.apiContracts.employee

import java.util.UUID

sealed class EmployeeModuleErrorDto(message: String): Exception(message){

    class InvalidEmployeeName(message: String?): EmployeeModuleErrorDto(
        "Invalid employee name: ${message?.let {": $it"  }}"
    )


    class InvalidEmployeeSurname(message: String?): EmployeeModuleErrorDto(
        "Invalid employee surname: ${message?.let {": $it"  }}"
    )


    class InvalidEmployeePatronymic(message: String?): EmployeeModuleErrorDto(
        "Invalid employee patronymic: ${message?.let {": $it"  }}"
    )


    class EmployeeAlreadyExists(val name: String, val surname: String,
                                val patronymic: String? = null): EmployeeModuleErrorDto(
        "Employee with name $name $surname ${patronymic ?: ""} already exists"
    )


    class FailedToCreateEmployee(val name: String,val surname: String,
                                 val patronymic: String? = null): EmployeeModuleErrorDto(
        "Failed to create employee with name $name $surname ${patronymic ?: ""}"
    )

    class EmployeeNotExist(val employeeId: UUID): EmployeeModuleErrorDto(
        "Employee with employee id $employeeId does not exist"
    )

    class InternalServerError : EmployeeModuleErrorDto("Internal employee module server error")
}
