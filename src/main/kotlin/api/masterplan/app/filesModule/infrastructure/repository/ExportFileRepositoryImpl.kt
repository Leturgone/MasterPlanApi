package api.masterplan.app.filesModule.infrastructure.repository

import api.masterplan.app.filesModule.domain.interfaces.ExportFileRepository
import api.masterplan.app.filesModule.domain.model.entity.ExportFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.infrastructure.excel.ExcelWorker
import org.springframework.stereotype.Repository

@Repository
class ExportFileRepositoryImpl(
    private val exelWorker: ExcelWorker
): ExportFileRepository {
    override fun <T: Any> exportListToExcel(fileName: DocumentFileBaseName, data: List<T>): ExportFile {
        val fileBytes = exelWorker.exportListToExcel(data)
        return ExportFile.create(
            fileData = DocumentFileData(fileBytes),
            baseName = fileName
        )
    }

}