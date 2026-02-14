package api.masterplan.app.userManagementModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Данные для проверки учетных данных")
data class ValidateCredentialsRequest(

    @NotBlank(message = "Login cant be blank")
    @Schema(description = "Логин", example = "MASTERPLAN_LOGIN")
    val login: String,

    @NotBlank(message = "Password cant be blank")
    @Schema(description = "Пароль", example = "MASTERPLAN_PASSWORD")
    val password: String
)
