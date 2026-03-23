package api.masterplan.app.filesModule.infrastructure.exel

interface ExelWorker {
    fun <T> exportListToExel(data: List<T>): ByteArray
}