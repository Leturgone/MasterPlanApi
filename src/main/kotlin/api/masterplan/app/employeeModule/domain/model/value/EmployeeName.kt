package api.masterplan.app.employeeModule.domain.model.value

import api.masterplan.app.employeeModule.domain.exceptions.EmployeeException

@JvmInline
value class EmployeeName(val value: String) {
    companion object {
        fun validate(name: String): EmployeeName {
            try {
                require(name.isNotBlank()) { "Name cant be blank" }
                require(name.length <= 45) { "Name too long" }
            } catch (e: IllegalArgumentException) {
                throw EmployeeException.InvalidEmployeeName(e.message)
            }
            return EmployeeName(name)
        }
    }
}
