package api.masterplan.app.filesModule.domain.model.value

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmInline
value class DocumentFileName(val value: String){
    companion object {
        fun generate(fileName: String):DocumentFileName{
            val timeSnap = DateTimeFormatter.ofPattern("yyyMMddHHmmss").format(LocalDateTime.now())
            val newFileName = "$timeSnap$fileName"
            return DocumentFileName(newFileName)
        }
    }
}
