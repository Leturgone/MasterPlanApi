package api.masterplan.app.filesModule.infrastructure.database.dto

import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

data class DocumentEntityDto(
    val id: DocumentFileId,
    val name: DocumentFileName,
    val path: String
)
