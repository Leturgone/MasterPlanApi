package api.masterplan.app.authModule.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Неудачный ответ")
data class AuthErrorResponse(
    @Schema(description = "HTTP код ошибки", example = "500")
    val status: Int,

    @Schema(description = "Описание HTTP ошибки", example = "Error while login")
    val message: String? = "",

    @Schema(description = "Дата ошибки ", example = "2026-01-27T18:30:00Z")
    val timestamp: LocalDateTime,
)