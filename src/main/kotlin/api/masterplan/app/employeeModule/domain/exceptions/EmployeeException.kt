package api.masterplan.app.employeeModule.domain.exceptions

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
}