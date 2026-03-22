package api.masterplan.app.filesModule.infrastructure.filesStorage.repository

interface FIleStorageRepository {

    fun isFileExist(fileName: String): Boolean

    fun writeFile(fileName: String, fileBytes: ByteArray): String?

    fun readFile(fileName: String): ByteArray?

    fun delete(fileName: String): String?
}