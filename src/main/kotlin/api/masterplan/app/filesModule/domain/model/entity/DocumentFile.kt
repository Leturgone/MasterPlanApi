package api.masterplan.app.filesModule.domain.model.entity

import api.masterplan.app.filesModule.domain.model.value.DocumentFileName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileId

@ConsistentCopyVisibility
data class DocumentFile private constructor(
    val fileId: DocumentFileId,
    val fileData: DocumentFileData,
    val fileName: DocumentFileName,
){
    companion object{
        fun create(id: DocumentFileId? = null, baseName: String, fileData: DocumentFileData): DocumentFile{
            val docId = id?:DocumentFileId.generate()
            val name = DocumentFileName.generate(baseName)
            return DocumentFile(
                fileId = docId,
                fileData = fileData,
                fileName = name
            )
        }

        fun create(id: DocumentFileId? = null, fileData: DocumentFileData, fileName: DocumentFileName): DocumentFile {
            val docId = id?:DocumentFileId.generate()
            return DocumentFile(
                fileId = docId,
                fileData = fileData,
                fileName = fileName
            )
        }
    }
}
