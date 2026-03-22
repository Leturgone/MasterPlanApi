package api.masterplan.app.filesModule.infrastructure.filesStorage.repository

interface FIleStorageRepository {
    fun writeFile(fileName: String, fileBytes: ByteArray): String?
    fun readFile(fileName: String): ByteArray?
    fun delete(fileName: String): Boolean
}