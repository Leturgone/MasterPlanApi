package api.masterplan.app.reportsModule.domain.exceptions

import api.masterplan.app.reportsModule.domain.models.value.ReportId
import api.masterplan.app.reportsModule.domain.models.value.ReportTitle
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportStatus
import api.masterplan.app.reportsModule.domain.models.value.ReportType
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportTitle

sealed class ReportException(message: String) : Exception(message) {

    class InvalidReportTitle(message: String?) : ReportException(
        "Invalid report title: ${message?.let { ": $it" } ?: ""}"
    )

    class InvalidReportDescription(message: String?) : ReportException(
        "Invalid report description: ${message?.let { ": $it" } ?: ""}"
    )

    class ReportNotExist(planReportId: ReportId, reportType: ReportType) : ReportException(
        "${reportType.name} Report with id = ${planReportId.id} not found"
    )


    class FailedToUpdateReport(planReportId: ReportId,reportType: ReportType) : ReportException(
        "Failed to update ${reportType.name} report with id = ${planReportId.id}"
    )

    class FailedToUpdateReportStatus(status: ReportStatus, planReportId: ReportId,reportType: ReportType) : ReportException(
        "Failed to update status ${status.name} for ${reportType.name} report with id = ${planReportId.id}"
    )


    class FailedToDeleteReport(planReportId: ReportId,reportType: ReportType) : ReportException(
        "Failed to delete ${reportType.name}  report with id = ${planReportId.id}"
    )


    class ReportAlreadyExist(employeeId: ReportEmployeeId,planReportTitle: ReportTitle) : ReportException(
        "Report with employeeId = ${employeeId.value} and title = ${planReportTitle.value} already exist"
    )


    class FailedToSaveReport(employeeId: ReportEmployeeId, planReportTitle: ReportTitle,reportType: ReportType) : ReportException(
        "Failed to save ${reportType.name}  report with employeeId = ${employeeId.value} and title = ${planReportTitle.value}"
    )

    class InternalServerError(message: String? = null) : ReportException("Internal report module server error: ${message?:""}")


}