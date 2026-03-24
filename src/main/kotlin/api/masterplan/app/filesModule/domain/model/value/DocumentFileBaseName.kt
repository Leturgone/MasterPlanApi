package api.masterplan.app.filesModule.domain.model.value

import api.masterplan.app.filesModule.domain.exceptions.FilesException

@JvmInline
value class DocumentFileBaseName(val value: String){
    companion object {
        fun validate(fileName: String):DocumentFileBaseName{
            try {
                require(fileName.isNotBlank()) { "File name cant be blank" }
                require(fileName.length <= 125) { "File name too long" }
            } catch (e: IllegalArgumentException) {
                throw FilesException.InvalidFileName(errorMessage = e.message)
            }
            return DocumentFileBaseName(fileName)
        }
    }
}
