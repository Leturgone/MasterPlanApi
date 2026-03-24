package api.masterplan.app.filesModule

import java.util.*

sealed class FilesModuleErrorDto(message : String) : Exception(message) {
    class InvalidFileName(val errorMessage: String?) : FilesModuleErrorDto(
        "Invalid file name: ${errorMessage?.let { ": $it" } ?: ""}"
    )

    class FileNotExist(val fileId: UUID): FilesModuleErrorDto(
        "File with id $fileId not found"
    )

    class FileAlreadyExists(val fileName: String): FilesModuleErrorDto(
        "File with name: $fileName already exists"
    )

    class FailedToCreateFile(val fileName: String): FilesModuleErrorDto(
        "Failed to create file with name: $fileName"
    )

    class FailedToUpdateFile(val fileId: UUID): FilesModuleErrorDto(
        "Failed to update file with id: $fileId"
    )

    class FailedToDeleteFile(val fileId: UUID): FilesModuleErrorDto(
        "Failed to delete file with id: $fileId"
    )

    class InternalServerError(message: String) : FilesModuleErrorDto(message)
}