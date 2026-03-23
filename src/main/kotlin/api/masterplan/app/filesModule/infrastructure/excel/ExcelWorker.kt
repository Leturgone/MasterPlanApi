package api.masterplan.app.filesModule.infrastructure.excel

interface ExcelWorker {
    fun <T: Any> exportListToExel(data: List<T>): ByteArray
}