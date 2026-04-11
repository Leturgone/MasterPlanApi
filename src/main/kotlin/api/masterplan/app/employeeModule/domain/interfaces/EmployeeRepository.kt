package api.masterplan.app.employeeModule.domain.interfaces

import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeUserId

interface EmployeeRepository {

    fun getAllEmployees(): List<Employee>

    fun getEmployeeById(employeeId: EmployeeId): Employee?

    fun getEmployeeByUserId(userId: EmployeeUserId): Employee?

    fun saveEmployee(employee: Employee): EmployeeId

    fun searchByNameOrSurname(query: String): List<Employee>

    fun searchByNameOrSurnameAndDirId(query: String,directorId: EmployeeId): List<Employee>

    fun findByDirectorId(directorId: EmployeeId): List<Employee>

    fun isEmployeeExist(userId: EmployeeUserId): Boolean

    fun updateEmployee(id: EmployeeId, newEmployee: Employee): Employee

}