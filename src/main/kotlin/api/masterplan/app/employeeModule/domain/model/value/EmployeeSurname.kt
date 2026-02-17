package api.masterplan.app.employeeModule.domain.model.value

import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException

@JvmInline
value class EmployeeSurname(val value: String) {
    companion object {
        fun validate(patronymic: String): EmployeeName {
            try {
                require(patronymic.isNotBlank()) { "Surname cant be blank" }
                require(patronymic.length <= 100) { "Surname too long" }
            } catch (e: IllegalArgumentException) {
                throw EmployeeException.InvalidEmployeeSurname(e.message)
            }
            return EmployeeName(patronymic)
        }
    }
}
