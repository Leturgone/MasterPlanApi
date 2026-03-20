package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.model.entity.DocumentFile
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

interface DocumentFileRepository {

    fun addFile(documentFileName: DocumentFileName,documentFileData: DocumentFileData): DocumentFileId

    fun removeFile(fileId: DocumentFileId):DocumentFileId

    fun updateFile(fileId: DocumentFileId? = null,documentFileData: DocumentFileData):DocumentFileId

    fun getFile(fileId: DocumentFileId): DocumentFile
}