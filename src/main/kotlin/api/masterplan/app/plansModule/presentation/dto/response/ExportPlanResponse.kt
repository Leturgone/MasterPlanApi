package api.masterplan.app.plansModule.presentation.dto.response

import org.springframework.http.HttpHeaders

data class ExportPlanResponse(
    val fileHeaders: HttpHeaders,
    val fileData: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExportPlanResponse

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
