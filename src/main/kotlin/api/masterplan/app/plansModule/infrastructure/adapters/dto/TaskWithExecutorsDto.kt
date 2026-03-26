package api.masterplan.app.plansModule.infrastructure.adapters.dto

import api.masterplan.app.export.annotation.ExportDisplayName
import java.time.LocalDate

data class TaskWithExecutorsDto(
    @ExportDisplayName("ID")
    val id: String,
    @ExportDisplayName("Название")
    val title: String,
    @ExportDisplayName("Описание")
    val description: String,
    @ExportDisplayName("Срок выполнения")
    val endDate: LocalDate,
    @ExportDisplayName("Статус выполнения")
    val status: String,
    @ExportDisplayName("Исполнители")
    val executorsIds: List<ExecutorExportData>
)
