package api.masterplan.app.authModule.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

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

    @Schema(description = "ID пользователя UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID
)


