package api.masterplan.app.filesModule.application.usecase

import api.masterplan.app.filesModule.application.command.DownloadFileCommand
import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.domain.interfaces.DocumentFileService
import org.springframework.stereotype.Service

@Service
class DownloadFileUseCase(
    private val documentFileService: DocumentFileService
) {
    operator fun invoke(command: DownloadFileCommand): Result<DocumentFileDetails>{
        return try {
            val file = documentFileService.downloadFile(command.fileId)
            Result.success(file)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}