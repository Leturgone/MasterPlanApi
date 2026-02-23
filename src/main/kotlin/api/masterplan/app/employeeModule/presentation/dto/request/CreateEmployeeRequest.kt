package api.masterplan.app.employeeModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Schema(description = "Данные для создания сотрудника")
data class CreateEmployeeRequest(
    @NotBlank(message = "Name cant be blank")
    @Schema(description = "Имя сотрудника", example = "Иван")
    val name: String,
    @NotBlank(message = "Surname cant be blank")
    @Schema(description = "Фамилия сотрудника", example = "Иванов")
    val surname: String,
    @Schema(description = "Отчество сотрудника", example = "Иванович")
    val patronymic: String? = null,
    @Schema(description = "ID руководителя сотрудника UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val directorId: UUID? = null,
    @Schema(description = "ID пользователя сотрудника UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val userId: UUID
)
