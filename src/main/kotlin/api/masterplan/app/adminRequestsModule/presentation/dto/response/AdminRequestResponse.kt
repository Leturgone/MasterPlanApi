package api.masterplan.app.adminRequestsModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "Ответ Заявка")
data class AdminRequestResponse(
    @Schema(description = "ID заявки UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @Schema(description = "Название заявки", example = "Заявка 1")
    val title: String,
    @Schema(description = "Описание заявки", example = "Создать аккаунт")
    val description: String,
    @Schema(description = "Дата создания заявки", example = "01.01.2026 12:00:00")
    val creationDate: LocalDateTime,
    @Schema(description = "ID создателя заявки UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val senderId: UUID,
    @Schema(description = "Статус обработки заявки", example = "IN_PROGRESS")
    val status: String
)
