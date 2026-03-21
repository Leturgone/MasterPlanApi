package api.masterplan.app.filesModule.domain.exceptions

import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

sealed class FilesException(message: String) : Exception(message) {

    class InvalidFileName(message: String?) : FilesException(
        "Invalid file name: ${message?.let { ": $it" } ?: ""}"
    )

    class FileNotExist(fileId: DocumentFileId): FilesException(
        "File with id ${fileId.value} not found"
    )

    class FileAlreadyExists(fileName: DocumentFileBaseName): FilesException(
        "File with name: ${fileName.value} already exists"
    )

    class FailedToCreateFile(fileName: DocumentFileName): FilesException(
        "Failed to create file with name: ${fileName.value}"
    )

    class FailedToUpdateFile(fileId: DocumentFileId): FilesException(
        "Failed to update file with id: ${fileId.value}"
    )

    class FailedToDeleteFile(fileId: DocumentFileId): FilesException(
        "Failed to delete file with id: ${fileId.value}"
    )

}