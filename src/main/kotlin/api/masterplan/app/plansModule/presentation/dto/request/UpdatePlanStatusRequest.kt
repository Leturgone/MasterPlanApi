package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Schema(description = "Данные для обновления статуса плана")
data class UpdatePlanStatusRequest(
    @NotBlank(message = "plan Id cant be blank")
    @Schema(description = "ID плана UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val planId: UUID,
    @NotBlank(message = "plan status cant be blank")
    @Schema(description = "Статус плана", example = "COMPLETED")
    val status: String
)