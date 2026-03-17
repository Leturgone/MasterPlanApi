package api.masterplan.app.reportsModule.domain.exceptions

import api.masterplan.app.reportsModule.domain.models.value.*

sealed class ReportException(message: String) : Exception(message) {

    class InvalidReportTitle(message: String?) : ReportException(
        "Invalid report title: ${message?.let { ": $it" } ?: ""}"
    )

    class InvalidReportDescription(message: String?) : ReportException(
        "Invalid report description: ${message?.let { ": $it" } ?: ""}"
    )

    class InvalidReportStatus(status: String) : ReportException(
        "Invalid report status: $status"
    )

    class InvalidReferenceId(ref: ReportReferenceId) : ReportException(
        "Invalid reference ID: $ref"
    )

    class ReportNotExist(planReportId: ReportId, reportType: ReportType) : ReportException(
        "${reportType.name} Report with id = ${planReportId.value} not found"
    )


    class FailedToUpdateReport(planReportId: ReportId,reportType: ReportType) : ReportException(
        "Failed to update ${reportType.name} report with id = ${planReportId.value}"
    )

    class FailedToUpdateReportStatus(status: ReportStatus, planReportId: ReportId,reportType: ReportType) : ReportException(
        "Failed to update status ${status.name} for ${reportType.name} report with id = ${planReportId.value}"
    )


    class FailedToDeleteReport(planReportId: ReportId,reportType: ReportType) : ReportException(
        "Failed to delete ${reportType.name}  report with id = ${planReportId.value}"
    )


    class ReportAlreadyExist(employeeId: ReportEmployeeId,planReportTitle: ReportTitle) : ReportException(
        "Report with employeeId = ${employeeId.value} and title = ${planReportTitle.value} already exist"
    )


    class FailedToSaveReport(employeeId: ReportEmployeeId, planReportTitle: ReportTitle,reportType: ReportType) : ReportException(
        "Failed to save ${reportType.name}  report with employeeId = ${employeeId.value} and title = ${planReportTitle.value}"
    )

    class InternalServerError(message: String? = null) : ReportException("Internal report module server error: ${message?:""}")


}