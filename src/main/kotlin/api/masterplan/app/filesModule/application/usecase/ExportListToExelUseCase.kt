package api.masterplan.app.filesModule.application.usecase

import api.masterplan.app.filesModule.application.command.ExportListToExelCommand
import api.masterplan.app.filesModule.domain.interfaces.ExportFileService
import api.masterplan.app.filesModule.domain.model.entity.ExportFile
import org.springframework.stereotype.Service

@Service
class ExportListToExelUseCase(
    private val exportFileService: ExportFileService
) {
    operator fun invoke(command: ExportListToExelCommand): Result<ExportFile> {
        return try {
            val exportedFile = exportFileService.exportListToExel(command)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}