package api.masterplan.app.userManagementModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Данные для сброса пароля")
data class ResetPasswordRequest(

    @Schema(description = "ID пользователя UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val userId: UUID,

    @Schema(description = "Пароль", example = "MASTERPLAN_PASSWORD")
    val password: String,
)
