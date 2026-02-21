package api.masterplan.app.employeeModule.application.dto

data class FileModel(
    val fileData: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileModel

        return fileData.contentEquals(other.fileData)
    }

    override fun hashCode(): Int {
        return fileData.contentHashCode()
    }
}
