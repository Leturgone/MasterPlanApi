package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.*

@Schema(description = "Данные для создания плана")
data class CreatePlanRequest(
    @Schema(description = "ID плана UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID? = null,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название плана", example = "План 1")
    val title: String,
    @NotBlank(message = "Description cant be blank")
    @Schema(description = "Описание плана", example = "Выполнить мероприятия для выполнения цели")
    val description: String,
    @Schema(description = "Дата начала выполнения плана", example = "01.01.2026")
    val startDate: LocalDate? = null,
    @NotBlank(message = "End date cant be blank")
    @Schema(description = "Финальный срок выполнения плана", example = "01.01.2026")
    val endDate: LocalDate,
    @NotBlank(message = "Director id cant be blank")
    @Schema(description = "ID руководителя плана", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val directorId: UUID,
    @Schema(description = "Название файла", example = "PlanExport23022026172732")
    val documentName: String? = null,
)
