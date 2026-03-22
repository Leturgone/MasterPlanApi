package api.masterplan.app.filesModule.domain.model.entity

import api.masterplan.app.filesModule.domain.model.value.DocumentFileBaseName
import api.masterplan.app.filesModule.domain.model.value.DocumentFileData
import api.masterplan.app.filesModule.domain.model.value.DocumentFileName

@ConsistentCopyVisibility
data class ExportFile private constructor(
    val fileData: DocumentFileData,
    val fileName: DocumentFileName
){
    companion object{
        fun create(baseName: DocumentFileBaseName,fileData: DocumentFileData): ExportFile{
            val name = DocumentFileName.generate(baseName)
            return ExportFile (
                fileData = fileData,
                fileName = name
            )
        }
    }
}

