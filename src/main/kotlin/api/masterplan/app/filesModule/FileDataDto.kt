package api.masterplan.app.filesModule

import java.util.UUID

data class FileDataDto(
    val fileId: UUID,
    val fileData: ByteArray,
    val fileName: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileDataDto

        if (fileId != other.fileId) return false
        if (!fileData.contentEquals(other.fileData)) return false
        if (fileName != other.fileName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileId.hashCode()
        result = 31 * result + fileData.contentHashCode()
        result = 31 * result + fileName.hashCode()
        return result
    }
}
