package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.*

@Schema(description = "Данные для создания задачи для плана мероприятий")
data class CreateTaskRequest (
    @Schema(description = "ID плана UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val planId: UUID,
    @Schema(description = "ID задачи UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val taskId: UUID? = null,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название задачи", example = "Задача 1")
    val title: String,
    @NotBlank(message = "Description cant be blank")
    @Schema(description = "Описание задачи", example = "Провести анализ")
    val description: String,
    @NotBlank(message = "End date cant be blank")
    @Schema(description = "Финальный срок выполнения задачи", example = "2026-05-06")
    val endDate: LocalDate,
    @NotBlank(message = "Executors ids be blank")
    @Schema(description = "Список id исполнителей в UUIDv7")
    val executorsIds: List<UUID>
)