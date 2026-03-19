package api.masterplan.app.reportsModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

@Schema(description = "Ответ Отчета")
data class ReportResponse(
    @Schema(description = "ID отчета UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @Schema(description = "Название Отчета", example = "Отчет 1")
    val title: String,
    @Schema(description = "Дата создания отчета", example = "01.01.2026 12:00:00")
    val creationDate: LocalDateTime,
    @Schema(description = "Дата обновления отчета", example = "01.01.2026 12:00:00")
    val editDate: LocalDateTime? = null,
    @Schema(description = "Описание отчета", example = "Отчет с заметкой")
    val description: String? = null,
    @Schema(description = "Статус проверки отчета", example = "NOT_CHECKED")
    val reportStatus: String,
    @Schema(description = "ID создателя отчета UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val employeeId: UUID,
    @Schema(description = "ID задачи или плана, на которую ссылается отчет UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val referenceId: UUID,
    @Schema(description = "Тип отчета", example = "TASK")
    val type: String,
    @Schema(description = "ID документа который прикреплен к отчету UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val documentId: UUID
)
