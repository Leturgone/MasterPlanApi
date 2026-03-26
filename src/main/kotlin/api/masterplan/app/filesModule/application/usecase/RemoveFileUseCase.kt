package api.masterplan.app.filesModule.application.usecase

import api.masterplan.app.filesModule.application.command.RemoveFileCommand
import api.masterplan.app.filesModule.domain.interfaces.DocumentFileService
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import org.springframework.stereotype.Service

// Внутренний юзкейс для использования между модулей
@Service
class RemoveFileUseCase(
    private val documentFileService: DocumentFileService
) {
    operator fun invoke(command: RemoveFileCommand): Result<DocumentFileId>{
        return try {
            val deletedFileId = documentFileService.removeFile(command.fileId)
            Result.success(deletedFileId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}