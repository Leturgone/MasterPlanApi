package api.masterplan.app.filesModule.application.service

import api.masterplan.app.filesModule.application.mapper.FilesToDetailsMapper
import api.masterplan.app.filesModule.domain.dtos.ExportFileDetails
import api.masterplan.app.filesModule.domain.interfaces.ExportFileRepository
import api.masterplan.app.filesModule.domain.interfaces.ExportFileService
import api.masterplan.app.logging.LoggingMethod
import org.springframework.stereotype.Service

@Service
class ExportFileServiceImpl(
    private val exportFileRepository: ExportFileRepository
): ExportFileService {

    @LoggingMethod("filesModule")
    override fun <T> exportListToExel(data: List<T>): ExportFileDetails {
        val file = exportFileRepository.exportListToExel(data)
        return FilesToDetailsMapper.toExportFileDetails(file)
    }


    @LoggingMethod("filesModule")
    override fun <T> exportSingletEntityToExel(data: T): ExportFileDetails {
        val file = exportFileRepository.exportSingletEntityToExel(data)
        return FilesToDetailsMapper.toExportFileDetails(file)
    }
}