package api.masterplan.app.filesModule.application.command

import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId

data class UpdateFileCommand(
    val fileId: DocumentFileId,
    val documentFileBaseName: DocumentFileBaseName,
    val documentFileData: DocumentFileData
)
