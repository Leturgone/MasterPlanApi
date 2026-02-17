package api.masterplan.app.userManagementModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Schema(description = "Данные для сброса пароля")
data class ResetPasswordRequest(

    @NotBlank(message = "UserId cant be blank")
    @Schema(description = "ID пользователя UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val userId: UUID,

    @NotBlank(message = "Password cant be blank")
    @Schema(description = "Пароль", example = "MASTERPLAN_PASSWORD")
    val password: String,
)
