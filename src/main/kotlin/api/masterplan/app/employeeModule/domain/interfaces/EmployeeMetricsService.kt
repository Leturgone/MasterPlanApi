package api.masterplan.app.employeeModule.domain.interfaces

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics

interface EmployeeMetricsService {

    fun calculateMetricsForEmployee(employeeId: EmployeeId): EmployeeMetrics

    fun calculateMetricsForEmployees(employees: Set<EmployeeId>): Map<EmployeeId,EmployeeMetrics>

}