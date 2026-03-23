package api.masterplan.app.filesModule

import java.util.*

interface FilesModuleService {

    fun <T: Any> exportListToExcel(fileName: String, data: List<T>): ExportFileDto

    fun uploadFile(documentFileBaseName: String, documentFileData: ByteArray): UUID

    fun removeFile(fileId: UUID): UUID

    fun updateFile(fileId: UUID, documentFileBaseName: String, documentFileData: ByteArray): UUID
}