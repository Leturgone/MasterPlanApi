package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

interface DocumentFileService {

    fun uploadFile(documentFileName: DocumentFileName,documentFileData: DocumentFileData): DocumentFileId

    fun removeFile(fileId: DocumentFileId):DocumentFileId

    fun uploadOrUpdateFile(fileId: DocumentFileId? = null,documentFileData: DocumentFileData):DocumentFileId

    fun downloadFile(fileId: DocumentFileId): DocumentFileDetails
}