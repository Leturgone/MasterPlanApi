package api.masterplan.app.filesModule.domain.dtos

import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

data class DocumentFileDetails(
    val fileId: DocumentFileId,
    val fileData: DocumentFileData,
    val fileName: DocumentFileName,
){
    override fun toString(): String {
        return "DocumentFileDetails(fileId=$fileId, fileName=$fileName)"
    }
}
