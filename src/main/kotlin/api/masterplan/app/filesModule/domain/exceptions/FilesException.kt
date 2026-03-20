package api.masterplan.app.filesModule.domain.exceptions

sealed class FilesException(message: String) : Exception(message) {

    class InvalidFileName(message: String?) : FilesException(
        "Invalid file name: ${message?.let { ": $it" } ?: ""}"
    )

}