package api.masterplan.app.filesModule.application.usecase

import api.masterplan.app.filesModule.application.command.UpdateFileCommand
import api.masterplan.app.filesModule.domain.interfaces.DocumentFileService
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import org.springframework.stereotype.Service

// Внутренний юзкейс для использования между модулей
@Service
class UpdateFileUseCase(
    private val documentFileService: DocumentFileService
){
    operator fun invoke(command:UpdateFileCommand): Result<DocumentFileId>{
        return try {
            val updatedFileId = documentFileService.updateFile(
                fileId = command.fileId,
                documentFileBaseName = command.documentFileBaseName,
                documentFileData = command.documentFileData
            )
            Result.success(updatedFileId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}
