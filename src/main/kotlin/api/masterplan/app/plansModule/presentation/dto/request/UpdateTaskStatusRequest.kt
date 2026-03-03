package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Schema(description = "Данные для обновления статуса задачи")
data class UpdateTaskStatusRequest(
    @NotBlank(message = "task Id cant be blank")
    @Schema(description = "ID задачи UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val taskId: UUID,
    @NotBlank(message = "task status cant be blank")
    @Schema(description = "Статус задачи", example = "COMPLETED")
    val status: String
)