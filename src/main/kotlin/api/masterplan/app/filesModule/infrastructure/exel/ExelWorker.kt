package api.masterplan.app.filesModule.infrastructure.exel

interface ExelWorker {
    fun <T: Any> exportListToExel(data: List<T>): ByteArray
}