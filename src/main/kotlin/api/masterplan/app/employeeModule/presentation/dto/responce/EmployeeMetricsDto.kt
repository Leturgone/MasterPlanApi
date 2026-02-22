package api.masterplan.app.employeeModule.presentation.dto.responce

data class EmployeeMetricsDto(
    val rating: Double,
    val workload: Double,
    val assignedTasksCount: Int
)
