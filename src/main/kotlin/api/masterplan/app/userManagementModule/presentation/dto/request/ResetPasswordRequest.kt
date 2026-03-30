package api.masterplan.app.userManagementModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Данные для сброса пароля")
data class ResetPasswordRequest(
    @NotBlank(message = "Password cant be blank")
    @Schema(description = "Пароль", example = "MASTERPLAN_PASSWORD")
    val password: String,
)
