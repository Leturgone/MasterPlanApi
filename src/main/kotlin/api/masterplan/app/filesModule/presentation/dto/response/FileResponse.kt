package api.masterplan.app.filesModule.presentation.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Ответ Успешно скачанный файл")
data class FileResponse(
    @Schema(description = "ID файла UUIDv7",
        example = "06115aa098-9277-0087-49a8-cb901fc2f7"
    )
    val fileId: UUID,
    @Schema(description = "Файл")
    val fileData: ByteArray,
    @Schema(description = "Название файла", example = "23022026172732Отчет")
    val fileName: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileResponse

        if (fileId != other.fileId) return false
        if (!fileData.contentEquals(other.fileData)) return false
        if (fileName != other.fileName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileId.hashCode()
        result = 31 * result + fileData.contentHashCode()
        result = 31 * result + fileName.hashCode()
        return result
    }
}
