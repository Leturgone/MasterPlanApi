package api.masterplan.app.filesModule.infrastructure.intermodule

import api.masterplan.app.apiContracts.files.FilesModuleErrorDto
import api.masterplan.app.filesModule.domain.exceptions.FilesException

object InterModuleFileToDtoErrorMapper {
    fun toDto(exception: Throwable): FilesModuleErrorDto{
        return when (exception) {
            is FilesException.FailedToCreateFile -> FilesModuleErrorDto.FailedToCreateFile(exception.fileName.value)
            is FilesException.FailedToDeleteFile -> FilesModuleErrorDto.FailedToDeleteFile(exception.fileId.value)
            is FilesException.FailedToUpdateFile -> FilesModuleErrorDto.FailedToUpdateFile(exception.fileId.value)
            is FilesException.FileAlreadyExists -> FilesModuleErrorDto.FileAlreadyExists(exception.fileName.value)
            is FilesException.FileNotExist -> FilesModuleErrorDto.FileNotExist(exception.fileId.value)
            is FilesException.InvalidFileName -> FilesModuleErrorDto.InvalidFileName(errorMessage = exception.errorMessage)
            else -> FilesModuleErrorDto.InternalServerError("Internal files module server error")
        }
    }
}