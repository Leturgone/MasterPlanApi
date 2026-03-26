package api.masterplan.app.apiContracts.files

import java.util.*

interface FilesModuleService {

    fun <T: Any> exportListToExcel(fileName: String, data: List<T>): Result<FileDataDto>

    fun uploadFile(documentFileBaseName: String, documentFileData: ByteArray): Result<UUID>

    fun removeFile(fileId: UUID): Result<UUID>

    fun updateFile(fileId: UUID, documentFileBaseName: String, documentFileData: ByteArray): Result<UUID>
}