package api.masterplan.app.filesModule.presentation.dto.response

data class FileResponse(
    val fileHeaders: org.springframework.http.HttpHeaders,
    val fileData: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileResponse

        if (fileHeaders != other.fileHeaders) return false
        if (!fileData.contentEquals(other.fileData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileHeaders.hashCode()
        result = 31 * result + fileData.contentHashCode()
        return result
    }
}
