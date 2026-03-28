package api.masterplan.app.filesModule.presentation.mapper

import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import java.util.UUID

object FIleRequestToDomainMapper {
    fun toDocumentFileId(fileId: UUID): DocumentFileId = DocumentFileId(fileId)
}