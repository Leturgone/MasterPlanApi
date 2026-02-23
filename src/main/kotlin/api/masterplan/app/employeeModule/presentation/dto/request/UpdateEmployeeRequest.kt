package api.masterplan.app.employeeModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Schema(description = "Данные для обновления сотрудника")
data class UpdateEmployeeRequest(
    @NotBlank(message = "Id cant be blank")
    @Schema(description = "ID сотрудника UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @NotBlank(message = "Name cant be blank")
    @Schema(description = "Новое имя сотрудника", example = "Иван")
    val newName: String,
    @NotBlank(message = "Surname cant be blank")
    @Schema(description = "Новая фамилия сотрудника", example = "Иванов")
    val newSurname: String,
    @Schema(description = "Новое отчество сотрудника", example = "Иванович")
    val newPatronymic: String? = null,
    @Schema(description = "Новое ID руководителя сотрудника UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val newDirectorId: UUID? = null,
    @NotBlank(message = "UserId cant be blank")
    @Schema(description = "ID пользователя сотрудника UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val userId: UUID,
)
