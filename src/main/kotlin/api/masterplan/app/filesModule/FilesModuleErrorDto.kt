package api.masterplan.app.filesModule

import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

sealed class FilesModuleErrorDto(message : String) : Exception(message) {
    class InvalidFileName(val errorMessage: String?) : FilesModuleErrorDto(
        "Invalid file name: ${errorMessage?.let { ": $it" } ?: ""}"
    )

    class FileNotExist(val fileId: DocumentFileId): FilesModuleErrorDto(
        "File with id ${fileId.value} not found"
    )

    class FileAlreadyExists(val fileName: DocumentFileBaseName): FilesModuleErrorDto(
        "File with name: ${fileName.value} already exists"
    )

    class FailedToCreateFile(val fileName: DocumentFileName): FilesModuleErrorDto(
        "Failed to create file with name: ${fileName.value}"
    )

    class FailedToUpdateFile(val fileId: DocumentFileId): FilesModuleErrorDto(
        "Failed to update file with id: ${fileId.value}"
    )

    class FailedToDeleteFile(val fileId: DocumentFileId): FilesModuleErrorDto(
        "Failed to delete file with id: ${fileId.value}"
    )

    class InternalServerError(message: String) : FilesModuleErrorDto(message)
}