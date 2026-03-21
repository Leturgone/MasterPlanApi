package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.dtos.ExportFileDetails

interface ExportFileService {
    fun <T> exportListToExel(data: List<T>): ExportFileDetails
}