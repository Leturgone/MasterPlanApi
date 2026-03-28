package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.UUID

@Schema(description = "Данные для обновления задачи плана")
data class UpdateTaskRequest (
    @NotBlank(message = "ID cant be blank")
    @Schema(description = "ID задачи UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название задачи", example = "Задача 1")
    val title: String,
    @NotBlank(message = "Description cant be blank")
    @Schema(description = "Описание задачи", example = "Провести анализ")
    val description: String,
    @Schema(description = "Срочность задачи (вес)",
        example = "5.0")
    val urgency: Double,
    @Schema(description = "Финальный срок выполнения задачи", example = "2026-05-06")
    val endDate: LocalDate,
    @Schema(description = "Статус выполнения задачи", example = "IN_PROGRESS")
    val status: String,
    @Schema(description = "ID плана UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val planId: UUID,
    @Schema(description = "ID документа", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val documentId: UUID? = null,
    @NotBlank(message = "Executors ids be blank")
    @Schema(description = "Список id исполнителей в UUIDv7")
    val executorsIds: List<UUID>
)