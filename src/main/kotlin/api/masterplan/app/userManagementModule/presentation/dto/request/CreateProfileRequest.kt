package api.masterplan.app.userManagementModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.util.UUID

@Schema(description = "Данные для создания профиля")
data class CreateProfileRequest(

    @NotBlank(message = "Login cant be blank")
    @Schema(description = "Логин", example = "MASTERPLAN_LOGIN")
    val login: String,

    @NotBlank(message = "Password cant be blank")
    @Schema(description = "Пароль", example = "MASTERPLAN_PASSWORD")
    val password: String,

    @NotEmpty(message = "Roles cant be empty")
    @Schema(description = "Роли пользователя", example = "ADMIN,DIRECTOR, EMPLOYEE")
    val roles: Set<String>,

    @NotBlank(message = "Name cant be blank")
    @Schema(description = "Имя пользователя", example = "Иван")
    val name: String,

    @NotBlank(message = "Surname cant be blank")
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    val surname: String,

    @Schema(description = "Отчество пользователя", example = "Иванович")
    val patronymic: String? = null,

    @Schema(description = "ID руководителя пользователя UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val directorId: UUID? =  null

)
