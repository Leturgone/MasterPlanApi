package api.masterplan.app.filesModule.domain.model.value

import api.masterplan.app.filesModule.domain.exceptions.FilesException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmInline
value class DocumentFileBaseName(val value: String){
    companion object {
        fun generate(fileName: String):DocumentFileBaseName{
            try {
                require(fileName.isNotBlank()) { "File name cant be blank" }
                require(fileName.length <= 125) { "File name too long" }
            } catch (e: IllegalArgumentException) {
                throw FilesException.InvalidFileName(e.message)
            }
            return DocumentFileBaseName(fileName)
        }
    }
}
