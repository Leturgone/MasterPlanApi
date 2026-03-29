package api.masterplan.app.adminRequestsModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Schema(description = "Данные для создания заявки администратору")
data class CreateAdminRequestRequest(
    @Schema(description = "ID заявки UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID? = null,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название заявки", example = "Заявка 1")
    val title: String,
    @NotBlank(message = "Description cant be blank")
    @Schema(description = "Описание заявки", example = "Создать аккаунты")
    val description: String
)
