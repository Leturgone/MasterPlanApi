package api.masterplan.app.reportsModule.presentation.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class UpdateReportRequest(
    val title: String,
    val description: String?,

    val documentId: UUID,
    @Schema(description = "Название файла", example = "PlanExport23022026172732")
    val documentName: String,
    @Schema(description = "Файл")
    val document: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdateReportRequest

        if (title != other.title) return false
        if (description != other.description) return false
        if (documentId != other.documentId) return false
        if (documentName != other.documentName) return false
        if (!document.contentEquals(other.document)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + documentId.hashCode()
        result = 31 * result + documentName.hashCode()
        result = 31 * result + document.contentHashCode()
        return result
    }

}
