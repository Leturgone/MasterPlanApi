package api.masterplan.app.employeeModule.domain.model.value

import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException

@JvmInline
value class EmployeePatronymic(val value: String) {
    companion object {
        fun validate(patronymic: String): EmployeeName {
            try {
                require(patronymic.isNotBlank()) { "Patronymic cant be blank" }
                require(patronymic.length <= 45) { "Patronymic too long" }
            } catch (e: IllegalArgumentException) {
                throw EmployeeException.InvalidEmployeePatronymic(e.message)
            }
            return EmployeeName(patronymic)
        }
    }
}
