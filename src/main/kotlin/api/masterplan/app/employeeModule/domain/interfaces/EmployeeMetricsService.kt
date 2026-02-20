package api.masterplan.app.employeeModule.domain.interfaces

import api.masterplan.app.employeeModule.domain.model.entity.Employee
import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics

interface EmployeeMetricsService {

    fun calculateMetricsForEmployee(employeeId: EmployeeId): EmployeeMetrics

    suspend fun calculateMetricsForEmployees(employees: List<Employee>): Map<Employee,EmployeeMetrics>

}