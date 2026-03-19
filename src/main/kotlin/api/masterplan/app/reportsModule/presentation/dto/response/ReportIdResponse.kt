package api.masterplan.app.reportsModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Ответ ID Отчета")
data class ReportIdResponse(
    @Schema(description = "ID отчета UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7"
    )
    val id: UUID
)
