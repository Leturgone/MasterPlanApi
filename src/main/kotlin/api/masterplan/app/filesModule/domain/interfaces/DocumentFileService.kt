package api.masterplan.app.filesModule.domain.interfaces

import api.masterplan.app.filesModule.domain.dtos.DocumentFileDetails
import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId

interface DocumentFileService {

    fun uploadFile(documentFileBaseName: DocumentFileBaseName, documentFileData: DocumentFileData): DocumentFileId

    fun removeFile(fileId: DocumentFileId):DocumentFileId

    fun uploadOrUpdateFile(fileId: DocumentFileId? = null,
                           documentFileBaseName: DocumentFileBaseName,
                           documentFileData: DocumentFileData):DocumentFileId

    fun downloadFile(fileId: DocumentFileId): DocumentFileDetails
}