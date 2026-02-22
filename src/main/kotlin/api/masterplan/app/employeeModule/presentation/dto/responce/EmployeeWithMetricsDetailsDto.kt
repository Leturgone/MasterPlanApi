package api.masterplan.app.employeeModule.presentation.dto.responce

import java.util.*

data class EmployeeWithMetricsDetailsDto(
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val director: DirectorDetailsDto? = null,
    val metrics: EmployeeMetricsDto,
)
