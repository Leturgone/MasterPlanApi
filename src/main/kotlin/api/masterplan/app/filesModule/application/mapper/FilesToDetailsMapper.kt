package api.masterplan.app.filesModule.application.mapper

import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.domain.dtos.ExportFileDetails
import api.masterplan.app.filesModule.domain.model.entity.DocumentFile
import api.masterplan.app.filesModule.domain.model.entity.ExportFile

object FilesToDetailsMapper {
    fun toDocumentFileDetails(file: DocumentFile): DocumentFileDetails {
        return DocumentFileDetails(
            fileId = file.fileId,
            fileName = file.fileName,
            fileData = file.fileData
        )
    }

    fun toExportFileDetails(file: ExportFile): ExportFileDetails {
        return ExportFileDetails(
            fileName = file.fileName,
            fileData = file.fileData
        )
    }
}