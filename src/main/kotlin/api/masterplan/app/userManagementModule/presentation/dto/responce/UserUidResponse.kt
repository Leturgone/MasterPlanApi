package api.masterplan.app.userManagementModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "Ответ ID Пользователя")
sealed class UserUidResponse private constructor() {

    @Schema(description = "Удачный ответ")
    data class Success(
        @Schema(description = "ID пользователя UUIDv7",
            example = "06115aa098-9277-0087-49a8-cb901fc2f7"
        )
        val uid: UUID
    ) : UserUidResponse()


    @Schema(description = "Неудачный ответ")
    data class Error(

        @Schema(description = "HTTP код ошибки", example = "500")
        val status: Int,

        @Schema(description = "Описание HTTP ошибки", example = "Error while login")
        val message: String? = "",

        @Schema(description = "Дата ошибки ", example = "2026-01-27T18:30:00Z")
        val timestamp: LocalDateTime,
    ): UserUidResponse()
}
