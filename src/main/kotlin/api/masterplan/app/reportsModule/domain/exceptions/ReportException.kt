package api.masterplan.app.reportsModule.domain.exceptions

import api.masterplan.app.reportsModule.domain.models.value.*
import java.util.*

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

    class InvalidReportType(type: String) : ReportException(
        "Invalid report type: $type"
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

    class InvalidReportFileName(val errorMessage: String?) : ReportException(
        "Invalid report file name: ${errorMessage?.let { ": $it" } ?: ""}"
    )

    class ReportFileNotExist(val fileId: UUID): ReportException(
        "Report file with id $fileId not found"
    )

    class ReportFileAlreadyExists(val fileName: String): ReportException(
        "Report file with name: $fileName already exists"
    )

    class FailedToCreateReportFile(val fileName: String): ReportException(
        "Failed to create report file with name: $fileName"
    )

    class FailedToUpdateReportFile(val fileId: UUID): ReportException(
        "Failed to update report file with id: $fileId"
    )

    class FailedToDeleteReportFile(val fileId: UUID): ReportException(
        "Failed to delete report file with id: $fileId"
    )
}