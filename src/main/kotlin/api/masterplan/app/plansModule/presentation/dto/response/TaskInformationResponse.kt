package api.masterplan.app.plansModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.util.UUID

@Schema(description = "Ответ Данные задачи")
data class TaskInformationResponse(
    @Schema(description = "ID задачи UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @Schema(description = "Название задачи", example = "Задача 1")
    val title: String,
    @Schema(description = "Описание задачи", example = "Провести анализ")
    val description: String,
    @Schema(description = "Финальный срок выполнения задачи", example = "01.01.2026")
    val endDate: LocalDate,
    @Schema(description = "Статус выполнения задачи", example = "IN_PROGRESS")
    val status: String,
    @Schema(description = "ID плана к которому принадлежит задача UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val planId: UUID,
    @Schema(description = "ID документа который прикреплен к задаче UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val documentId: UUID? = null,
    @Schema(description = "Срочность задачи (вес)",
        example = "5.0")
    val urgency: Double,
    @Schema(description = "Список id исполнителей в UUIDv7")
    val executorsIds: List<UUID>
)
