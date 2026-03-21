package api.masterplan.app.filesModule.application.usecase

import api.masterplan.app.filesModule.application.command.ExportListToExelCommand
import api.masterplan.app.filesModule.domain.dtos.ExportFileDetails
import api.masterplan.app.filesModule.domain.interfaces.ExportFileService
import org.springframework.stereotype.Service

@Service
class ExportListToExelUseCase(
    private val exportFileService: ExportFileService
) {
    operator fun <T> invoke(command: ExportListToExelCommand<T> ): Result<ExportFileDetails> {
        return try {
            val exportedFile = exportFileService.exportListToExel(command.data)
            Result.success(exportedFile)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}