package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.model.entity.ExportFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName

interface ExportFileRepository {
    fun <T: Any> exportListToExcel(fileName: DocumentFileBaseName, data: List<T>): ExportFile
}