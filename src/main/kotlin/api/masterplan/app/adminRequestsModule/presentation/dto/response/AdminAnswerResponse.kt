package api.masterplan.app.adminRequestsModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Ответ Ответ на заявку")
data class AdminAnswerResponse(
    @Schema(description = "ID ответа на заявку UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @Schema(description = "Название ответа", example = "Ответ 1")
    val title: String,
    @Schema(description = "Описание ответа", example = "Аккаунт создан")
    val description: String,
    @Schema(description = "ID заявки UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val adminRequestId: UUID
)
