package api.masterplan.app.employeeModule.domain.interfaces

import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId

interface EmployeeRepository {

    fun getAllEmployees(): List<Employee>

    fun getEmployeeById(employeeId: EmployeeId): Employee?

    fun saveEmployee(employee: Employee): EmployeeId?

    fun searchByNameOrSurname(query: String): List<Employee>

    fun findByDirectorId(directorId: EmployeeId): List<Employee>

    fun updateEmployee(id: EmployeeId, newEmployee: Employee): Employee?

}