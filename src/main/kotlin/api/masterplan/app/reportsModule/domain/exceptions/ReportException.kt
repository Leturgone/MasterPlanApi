package api.masterplan.app.reportsModule.domain.exceptions

sealed class ReportException(message: String) : Exception(message) {

    class InvalidReportTitle(message: String?) : ReportException(
        "Invalid report title: ${message?.let { ": $it" } ?: ""}"
    )

    class InvalidReportDescription(message: String?) : ReportException(
        "Invalid report description: ${message?.let { ": $it" } ?: ""}"
    )

}