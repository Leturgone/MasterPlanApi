package api.masterplan.app.filesModule.application.command

import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData

data class UploadFileCommand(
    val documentFileBaseName: DocumentFileBaseName,
    val documentFileData: DocumentFileData
)