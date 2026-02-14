package api.masterplan.app.userManagementModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Данные для проверки учетных данных")
data class ValidateCredentialsRequest(

    @Schema(description = "Логин", example = "MASTERPLAN_LOGIN")
    val login: String,

    @Schema(description = "Пароль", example = "MASTERPLAN_PASSWORD")
    val password: String
)
