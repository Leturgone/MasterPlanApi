package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.UUID

@Schema(description = "Данные для обновления плана")
data class UpdatePlanRequest(
    @NotBlank(message = "ID cant be blank")
    @Schema(description = "ID плана UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название плана", example = "План 1")
    val title: String,
    @NotBlank(message = "Description cant be blank")
    @Schema(description = "Описание плана", example = "Выполнить мероприятия для выполнения цели")
    val description: String,
    @Schema(description = "Дата начала выполнения плана", example = "2026-05-06")
    val startDate: LocalDate? = null,
    @NotBlank(message = "End date cant be blank")
    @Schema(description = "Финальный срок выполнения плана", example = "2026-05-06")
    val endDate: LocalDate,
    @NotBlank(message = "Director id cant be blank")
    @Schema(description = "ID руководителя плана", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val directorId: UUID,
    @Schema(description = "ID документа", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val documentId: UUID? = null,
    @NotBlank(message = "plan status cant be blank")
    @Schema(description = "Статус плана", example = "COMPLETED")
    val status: String
)
