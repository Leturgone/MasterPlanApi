package api.masterplan.app.reportsModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.*

@Schema(description = "Данные для создания отчета")
data class CreateReportRequest(
    @Schema(description = "ID отчета UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val id: UUID? = null,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название Отчета", example = "Отчет 1")
    val title: String,
    @Schema(description = "Описание отчета", example = "Отчет с заметкой")
    val description: String? = null,
    @Schema(description = "ID создателя отчета UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val employeeId: UUID,
    @Schema(description = "ID задачи или плана, на которую ссылается отчет UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val referenceId: UUID,
    @Schema(description = "Название файла", example = "PlanExport23022026172732")
    val documentName: String,
    @Schema(description = "Файл")
    val document: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreateReportRequest

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (employeeId != other.employeeId) return false
        if (referenceId != other.referenceId) return false
        if (documentName != other.documentName) return false
        if (!document.contentEquals(other.document)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + title.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + employeeId.hashCode()
        result = 31 * result + referenceId.hashCode()
        result = 31 * result + documentName.hashCode()
        result = 31 * result + document.contentHashCode()
        return result
    }

}
