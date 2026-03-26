package api.masterplan.app.filesModule.application.command

import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName

data class ExportListToExelCommand<T: Any>(
    val fileName: DocumentFileBaseName,
    val data: List<T>
)