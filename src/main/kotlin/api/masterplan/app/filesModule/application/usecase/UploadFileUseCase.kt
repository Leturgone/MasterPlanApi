package api.masterplan.app.filesModule.application.usecase

import api.masterplan.app.filesModule.application.command.UploadFileCommand
import api.masterplan.app.filesModule.domain.interfaces.DocumentFileService
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import org.springframework.stereotype.Service

@Service
class UploadFileUseCase(
    private val documentFileService: DocumentFileService
) {
    operator fun invoke(command: UploadFileCommand):Result<DocumentFileId>{
        return try {
            val uploadedFileId = documentFileService.uploadFile(
                documentFileBaseName = command.documentFileBaseName,
                documentFileData = command.documentFileData
            )
            Result.success(uploadedFileId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}