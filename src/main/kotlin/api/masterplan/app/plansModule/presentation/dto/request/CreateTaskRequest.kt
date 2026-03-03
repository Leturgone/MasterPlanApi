package api.masterplan.app.plansModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.*

@Schema(description = "Данные для создания задачи для плана мероприятий")
data class CreateTaskRequest (
    @Schema(description = "ID плана UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val planId: UUID,
    @Schema(description = "ID задачи UUIDv7", example = "06115aa098-9277-0087-49a8-cb901fc2f7")
    val taskId: UUID? = null,
    @NotBlank(message = "Title cant be blank")
    @Schema(description = "Название задачи", example = "Задача 1")
    val title: String,
    @NotBlank(message = "Description cant be blank")
    @Schema(description = "Описание задачи", example = "Провести анализ")
    val description: String,
    @NotBlank(message = "End date cant be blank")
    @Schema(description = "Финальный срок выполнения задачи", example = "01.01.2026")
    val endDate: LocalDate,
    @Schema(description = "Название файла", example = "PlanExport23022026172732")
    val documentName: String? = null,
    @Schema(description = "Файл")
    val document: ByteArray? = null,
    @NotBlank(message = "Executors ids be blank")
    @Schema(description = "Список id исполнителей в UUIDv7")
    val executorsIds: List<UUID>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreateTaskRequest

        if (planId != other.planId) return false
        if (taskId != other.taskId) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (endDate != other.endDate) return false
        if (documentName != other.documentName) return false
        if (!document.contentEquals(other.document)) return false
        if (executorsIds != other.executorsIds) return false

        return true
    }

    override fun hashCode(): Int {
        var result = planId.hashCode()
        result = 31 * result + (taskId?.hashCode() ?: 0)
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + endDate.hashCode()
        result = 31 * result + (documentName?.hashCode() ?: 0)
        result = 31 * result + (document?.contentHashCode() ?: 0)
        result = 31 * result + executorsIds.hashCode()
        return result
    }
}