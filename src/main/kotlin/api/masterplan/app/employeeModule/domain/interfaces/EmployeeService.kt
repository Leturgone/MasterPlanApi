package api.masterplan.app.employeeModule.domain.interfaces

import api.masterplan.app.employeeModule.domain.dtos.EmployeeDetails
import api.masterplan.app.employeeModule.domain.dtos.EmployeeWithMetricsDetails
import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeName
import api.masterplan.app.employeeModule.domain.model.value.EmployeePatronymic
import api.masterplan.app.employeeModule.domain.model.value.EmployeeSurname

interface EmployeeService {

    fun getAllEmployees(): List<EmployeeDetails>

    fun getEmployeeById(id: EmployeeId): EmployeeDetails

    fun searchEmployee(query: String): List<EmployeeDetails>

    fun getAllDirectorsEmployee(directorId: EmployeeId): List<EmployeeDetails>

    fun getAllDirectorsEmployeeSortByRating(directorId: EmployeeId): List<EmployeeDetails>

    fun getAllDirectorsEmployeeSortByTaskCount(directorId: EmployeeId): List<EmployeeDetails>

    fun createEmployee(id: EmployeeId? = null, name: EmployeeName, surname: EmployeeSurname,
                       patronymic: EmployeePatronymic? = null, directorId: EmployeeId? = null): EmployeeId

    fun updateEmployee(id: EmployeeId, newEmployee: Employee): EmployeeDetails

    fun getMyProfile(currentEmployeeId: EmployeeId): EmployeeWithMetricsDetails

    fun getEmployeeWithMetrics(employeeId: EmployeeId): EmployeeWithMetricsDetails

}