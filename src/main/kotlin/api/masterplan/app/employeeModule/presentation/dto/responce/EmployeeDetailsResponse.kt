package api.masterplan.app.employeeModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Ответ Данные Сотрудника")
data class EmployeeDetailsResponse (
    @Schema(description = "ID сотрудника UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @Schema(description = "Имя сотрудника", example = "Иван")
    val name: String,
    @Schema(description = "Фамилия сотрудника", example = "Иванов")
    val surname: String,
    @Schema(description = "Отчество сотрудника", example = "Иванович")
    val patronymic: String? = null,
    @Schema(description = "ID руководителя сотрудника UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val directorId: UUID? = null,
    @Schema(description = "ID пользователя сотрудника UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val userId: UUID,
)
