package api.masterplan.app.userManagementModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Ответ c данными пользователя")
data class UserDataResponse(
    @Schema(description = "ID пользователя UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,

    @Schema(description = "Логин", example = "MASTERPLAN_LOGIN")
    val login: String,

    @Schema(description = "Хэш пароля")
    val password: String,

    @Schema(description = "Роли пользователя", example = "ADMIN,DIRECTOR, EMPLOYEE")
    val roles: Set<String>
)
