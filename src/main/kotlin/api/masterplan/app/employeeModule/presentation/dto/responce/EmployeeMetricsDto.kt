package api.masterplan.app.employeeModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "Данные сотрудника с метриками, полные данные о сотруднике")
data class EmployeeMetricsDto(
    @Schema(description = "Рейтинг сотрудника", example = "5.5")
    val rating: Double,
    @Schema(description = "Загруженность сотрудника", example = "3.0")
    val workload: Double,
    @Schema(description = "Число порученных сотруднику задач", example = "10")
    val assignedTasksCount: Int
)
