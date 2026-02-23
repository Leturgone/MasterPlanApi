package api.masterplan.app.employeeModule.presentation.dto.responce

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Ответ Файл с экспортрованными пользователями")
sealed class EmployeeFileResponse private constructor(){

    @Schema(description = "Удачный ответ")
    data class Success(
        @Schema(description = "Файл")
        val fileData: ByteArray,
        @Schema(description = "Название файла", example = "EmployeesExport23022026172732")
        val fileName: String,
    ):EmployeeFileResponse() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

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

    @Schema(description = "Неудачный ответ")
    data class Error(

        @Schema(description = "HTTP код ошибки", example = "500")
        val status: Int,

        @Schema(description = "Описание HTTP ошибки", example = "Error while login")
        val message: String? = "",

        @Schema(description = "Дата ошибки ", example = "2026-01-27T18:30:00Z")
        val timestamp: LocalDateTime,
    ): EmployeeFileResponse()
}
