package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.UUID

@Schema(description = "Данные для обновления плана")
data class UpdatePlanRequest(
    @NotBlank(message = "ID cant be blank")
    @Schema(description = "ID плана UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название плана", example = "План 1")
    val title: String,
    @NotBlank(message = "Description cant be blank")
    @Schema(description = "Описание плана", example = "Выполнить мероприятия для выполнения цели")
    val description: String,
    @Schema(description = "Дата начала выполнения плана", example = "01.01.2026")
    val startDate: LocalDate? = null,
    @NotBlank(message = "End date cant be blank")
    @Schema(description = "Финальный срок выполнения плана", example = "01.01.2026")
    val endDate: LocalDate,
    @NotBlank(message = "Director id cant be blank")
    @Schema(description = "ID руководителя плана", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val directorId: UUID,
    @Schema(description = "ID документа", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val documentId: UUID? = null,
    @Schema(description = "Название файла", example = "PlanExport23022026172732")
    val documentName: String? = null,
    @NotBlank(message = "plan status cant be blank")
    @Schema(description = "Статус плана", example = "COMPLETED")
    val status: String,
    @Schema(description = "Файл")
    val document: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdatePlanRequest

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (directorId != other.directorId) return false
        if (documentId != other.documentId) return false
        if (documentName != other.documentName) return false
        if (status != other.status) return false
        if (!document.contentEquals(other.document)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + endDate.hashCode()
        result = 31 * result + directorId.hashCode()
        result = 31 * result + (documentId?.hashCode() ?: 0)
        result = 31 * result + (documentName?.hashCode() ?: 0)
        result = 31 * result + status.hashCode()
        result = 31 * result + (document?.contentHashCode() ?: 0)
        return result
    }
}
