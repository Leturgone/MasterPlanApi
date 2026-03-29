package api.masterplan.app.adminRequestsModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Ответ ID Ответа")
data class AdminAnswerIdResponse(
    @Schema(description = "ID ответа UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7"
    )
    val id: UUID
)