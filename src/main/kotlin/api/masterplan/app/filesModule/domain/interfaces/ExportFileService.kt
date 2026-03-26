package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.dtos.ExportFileDetails
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName

interface ExportFileService {
    fun <T: Any> exportListToExcel(fileName: DocumentFileBaseName, data: List<T>): ExportFileDetails
}