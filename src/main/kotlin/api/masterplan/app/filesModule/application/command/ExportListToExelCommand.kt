package api.masterplan.app.filesModule.application.command

data class ExportListToExelCommand<T>(
    val data: List<T>
)