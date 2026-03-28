package api.masterplan.app.reportsModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class UpdateReportRequest(
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название Отчета", example = "Отчет 1")
    val title: String,
    @Schema(description = "Описание отчета", example = "Отчет с заметкой")
    val description: String?,
    @Schema(description = "ID файла UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val documentId: UUID
)
