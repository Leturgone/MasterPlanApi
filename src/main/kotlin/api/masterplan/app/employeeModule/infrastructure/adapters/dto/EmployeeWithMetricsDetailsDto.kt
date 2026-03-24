package api.masterplan.app.employeeModule.infrastructure.adapters.dto

import api.masterplan.app.export.ExportDisplayName

data class EmployeeWithMetricsDetailsDto(
    @ExportDisplayName("ID")
    val id: String,
    @ExportDisplayName("Имя")
    val name: String,
    @ExportDisplayName("Фамилия")
    val surname: String,
    @ExportDisplayName("Отчество")
    val patronymic: String? = null,
    @ExportDisplayName("Имя руководителя")
    val directorName: String? = null,
    @ExportDisplayName("Фамилия руководителя")
    val directorSurname: String? = null,
    @ExportDisplayName("Отчество руководителя")
    val directorPatronymic: String? = null,
    @ExportDisplayName("Рейтинг")
    val rating: Double,
    @ExportDisplayName("Нагрузка")
    val workload: Double,
    @ExportDisplayName("Количество порученных задач")
    val assignedTasksCount: Int
)