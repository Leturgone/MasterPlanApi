package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.model.entity.DocumentFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

interface DocumentFileRepository {

    fun saveFile(documentFile: DocumentFile): DocumentFileId?

    fun removeFile(fileId: DocumentFileId,oldFileName: DocumentFileName):DocumentFileId?

    fun updateFile(fileId: DocumentFileId,oldFileName: DocumentFileName,updatedDocumentFile: DocumentFile):DocumentFileId?

    fun getFile(fileId: DocumentFileId): DocumentFile?
}