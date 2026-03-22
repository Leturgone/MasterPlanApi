package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.model.entity.DocumentFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId

interface DocumentFileRepository {

    fun isFileExist(documentFileBaseName: DocumentFileBaseName): Boolean

    fun saveFile(documentFile: DocumentFile): DocumentFileId?

    fun removeFile(fileId: DocumentFileId):DocumentFileId?

    fun updateFile(fileId: DocumentFileId,documentFileData: DocumentFile):DocumentFileId?

    fun getFile(fileId: DocumentFileId): DocumentFile?
}