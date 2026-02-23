package api.masterplan.app.employeeModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Данные о руководителе")
data class DirectorDetailsDto(
    @Schema(description = "Имя пользователя", example = "Иван")
    val name: String,
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    val surname: String,
    @Schema(description = "Отчество пользователя", example = "Иванович")
    val patronymic: String? = null,
)
