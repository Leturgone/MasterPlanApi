package api.masterplan.app.employeeModule.domain.interfaces

import api.masterplan.app.employeeModule.domain.model.value.EmployeeId
import api.masterplan.app.employeeModule.domain.model.value.EmployeeMetrics

interface EmployeeMetricsService {

    fun calculateMetrics(employeeId: EmployeeId): EmployeeMetrics

}