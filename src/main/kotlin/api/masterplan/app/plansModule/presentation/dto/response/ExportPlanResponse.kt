package api.masterplan.app.plansModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ответ Файл с экспортрованным планом")
data class ExportPlanResponse(
    @Schema(description = "Файл")
    val fileData: ByteArray,
    @Schema(description = "Название файла", example = "PlanExport23022026172732")
    val fileName: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExportPlanResponse

        if (!fileData.contentEquals(other.fileData)) return false
        if (fileName != other.fileName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileData.contentHashCode()
        result = 31 * result + fileName.hashCode()
        return result
    }

}
