package api.masterplan.app.filesModule.infrastructure.excel

interface ExcelWorker {
    fun <T: Any> exportListToExcel(data: List<T>): ByteArray
}