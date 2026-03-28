package api.masterplan.app.filesModule.domain.model.value

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmInline
value class DocumentFileName(val value: String){
    companion object {
        fun generate(fileName: DocumentFileBaseName):DocumentFileName{
            val timeSnap = DateTimeFormatter.ofPattern("yyyMMddHHmmss").format(LocalDateTime.now())
            val newFileName = "$timeSnap${fileName.value}"
            return DocumentFileName(newFileName)
        }
        fun generateExportName(fileName: DocumentFileBaseName):DocumentFileName{
            val timeSnap = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())
            val newFileName = "export_$timeSnap${fileName.value}.xlsx"
            return DocumentFileName(newFileName)
        }
    }
}
