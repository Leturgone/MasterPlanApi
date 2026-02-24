package api.masterplan.app.employeeModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ответ Файл с экспортрованными пользователями")
data class EmployeeFileResponse(
    @Schema(description = "Файл")
    val fileData: ByteArray,
    @Schema(description = "Название файла", example = "EmployeesExport23022026172732")
    val fileName: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmployeeFileResponse

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
