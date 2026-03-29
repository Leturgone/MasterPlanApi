package api.masterplan.app.adminRequestsModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Данные для обновления статуса заявки")
data class UpdateRequestStatusRequest(
    @NotBlank(message = "admin request status cant be blank")
    @Schema(description = "Статус заявки", example = "IN_PROGRESS")
    val status: String
)
