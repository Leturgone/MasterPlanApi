package api.masterplan.app.authModule.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Ответ на аутентификацию")
sealed class LoginResponse private constructor() {

    @Schema(description = "Удачный ответ")
    data class Success(

        @Schema(description = "JWT‑токен для доступа к  API",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODk..."
        )
        val token: String,

        @Schema(
            description = "Тип токена",
            example = "Bearer",
            defaultValue = "Bearer"
        )
        val type: String = "Bearer",
    ): LoginResponse()

    @Schema(description = "Неудачный ответ")
    data class Error(

        @Schema(description = "HTTP код ошибки", example = "500")
        val status: Int,

        @Schema(description = "Описание HTTP ошибки", example = "Error while login")
        val message: String? = "",

        @Schema(description = "Дата ошибки ", example = "2026-01-27T18:30:00Z")
        val timestamp: LocalDateTime,
    ): LoginResponse()

}



