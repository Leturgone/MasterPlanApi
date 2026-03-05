package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Данные для обновления статуса плана")
data class UpdatePlanStatusRequest(
    @NotBlank(message = "plan status cant be blank")
    @Schema(description = "Статус плана", example = "COMPLETED")
    val status: String
)