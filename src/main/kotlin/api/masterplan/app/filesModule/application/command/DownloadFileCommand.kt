package api.masterplan.app.filesModule.application.command

import api.masterplan.app.filesModule.domain.model.value.DocumentFileId

data class DownloadFileCommand(
    val fileId: DocumentFileId
)