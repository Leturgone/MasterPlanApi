package api.masterplan.app.filesModule.domain.dtos

import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

data class ExportFileDetails(
    val fileData: DocumentFileData,
    val fileName: DocumentFileName,
)
