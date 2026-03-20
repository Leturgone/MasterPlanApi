package api.masterplan.app.filesModule.domain.model.value

import api.masterplan.app.filesModule.domain.exceptions.FilesException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmInline
value class DocumentFileName(val value: String){
    companion object {
        fun generate(fileName: String):DocumentFileName{
            try {
                require(fileName.isNotBlank()) { "File name cant be blank" }
                require(fileName.length <= 125) { "File name too long" }
            } catch (e: IllegalArgumentException) {
                throw FilesException.InvalidFileName(e.message)
            }
            val timeSnap = DateTimeFormatter.ofPattern("yyyMMddHHmmss").format(LocalDateTime.now())
            val newFileName = "$timeSnap$fileName"
            return DocumentFileName(newFileName)
        }
    }
}
