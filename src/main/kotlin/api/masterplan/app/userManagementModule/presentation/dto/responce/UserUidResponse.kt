package api.masterplan.app.userManagementModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Ответ ID Пользователя")
data class UserUidResponse(
    @Schema(description = "ID пользователя UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7"
    )
    val uid: UUID
)
