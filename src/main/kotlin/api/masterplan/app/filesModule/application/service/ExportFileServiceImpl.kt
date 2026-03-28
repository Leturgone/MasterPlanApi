package api.masterplan.app.filesModule.application.service

import api.masterplan.app.filesModule.application.mapper.FilesToDetailsMapper
import api.masterplan.app.filesModule.domain.dtos.ExportFileDetails
import api.masterplan.app.filesModule.domain.interfaces.ExportFileRepository
import api.masterplan.app.filesModule.domain.interfaces.ExportFileService
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.logging.annotations.LoggingMethod
import org.springframework.stereotype.Service

@Service
class ExportFileServiceImpl(
    private val exportFileRepository: ExportFileRepository
): ExportFileService {

    @LoggingMethod("filesModule")
    override fun <T: Any> exportListToExcel(fileName: DocumentFileBaseName, data: List<T>): ExportFileDetails {
        val file = exportFileRepository.exportListToExcel(
            fileName = fileName,
            data = data
        )
        return FilesToDetailsMapper.toExportFileDetails(file)
    }
}