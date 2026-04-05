package api.masterplan.app.authModule.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Ответ на аутентификацию")
data class LoginResponse(
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

    @Schema(description = "Роли пользователя",
        example = "ADMIN,DIRECTOR"
    )
    val roles: List<String>,
)


