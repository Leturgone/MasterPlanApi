package api.masterplan.app.authModule.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Данные для аутентификации")
data class LoginRequest(

    @Schema(description = "Логин", example = "MASTERPLAN_LOGIN")
    val login: String,

    @Schema(description = "Пароль", example = "MASTERPLAN_PASSWORD")
    val password: String
)
