package api.masterplan.app.adminRequestsModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Schema(description = "Данные для создания ответа на заявку")
data class CreateAdminAnswerRequest(
    @Schema(description = "ID ответа UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID? = null,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название Ответа", example = "Ответ 1")
    val title: String,
    @NotBlank(message = "Description cant be blank")
    @Schema(description = "Описание ответа", example = "Заявка выполнена")
    val description: String,
)
