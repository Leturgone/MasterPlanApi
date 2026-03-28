package api.masterplan.app.filesModule.domain.exceptions

import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

sealed class FilesException(message: String) : Exception(message) {

    class InvalidFileName(val errorMessage: String?) : FilesException(
        "Invalid file name: ${errorMessage?.let { ": $it" } ?: ""}"
    )

    class FileNotExist(val fileId: DocumentFileId): FilesException(
        "File with id ${fileId.value} not found"
    )

    class FileAlreadyExists(val fileName: DocumentFileBaseName): FilesException(
        "File with name: ${fileName.value} already exists"
    )

    class FailedToCreateFile(val fileName: DocumentFileName): FilesException(
        "Failed to create file with name: ${fileName.value}"
    )

    class FailedToUpdateFile(val fileId: DocumentFileId): FilesException(
        "Failed to update file with id: ${fileId.value}"
    )

    class FailedToDeleteFile(val fileId: DocumentFileId): FilesException(
        "Failed to delete file with id: ${fileId.value}"
    )

}