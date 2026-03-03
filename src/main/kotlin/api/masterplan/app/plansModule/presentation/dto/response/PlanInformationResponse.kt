package api.masterplan.app.plansModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.util.*

@Schema(description = "Ответ Данные плана мероприятий")
data class PlanInformationResponse(
    @Schema(description = "ID плана UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @Schema(description = "Название плана", example = "План 1")
    val title: String,
    @Schema(description = "Описание плана", example = "Выполнить мероприятия для выполнения цели")
    val description: String,
    @Schema(description = "Дата начала выполнения плана", example = "01.01.2026")
    val startDate: LocalDate,
    @Schema(description = "Финальный срок выполнения плана", example = "01.01.2026")
    val endDate: LocalDate,
    @Schema(description = "Статус выполнения плана", example = "IN_PROGRESS")
    val status: String,
    @Schema(description = "ID руководителя плана", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val directorId: UUID? = null,
    @Schema(description = "ID документа который прикреплен к плану UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val documentId: UUID? = null
)
