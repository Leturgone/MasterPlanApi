package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.model.entity.ExportFile

interface ExportFileRepository {
    fun <T> exportListToExel(data: List<T>): ExportFile
    fun <T> exportSingletEntityToExel(data: T): ExportFile
}