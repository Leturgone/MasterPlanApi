package api.masterplan.app.reportsModule.domain.exceptions

import api.masterplan.app.reportsModule.domain.models.value.PlanReportId
import api.masterplan.app.reportsModule.domain.models.value.PlanReportTitle
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportTitle

sealed class ReportException(message: String) : Exception(message) {

    class InvalidReportTitle(message: String?) : ReportException(
        "Invalid report title: ${message?.let { ": $it" } ?: ""}"
    )

    class InvalidReportDescription(message: String?) : ReportException(
        "Invalid report description: ${message?.let { ": $it" } ?: ""}"
    )

    class PlanReportNotExist(planReportId: PlanReportId) : ReportException(
        "Plan Report with id = ${planReportId.id} not found"
    )

    class TaskReportNotExist(taskReportId: TaskReportId) : ReportException(
        "Task Report with id = ${taskReportId.id} not found"
    )

    class FailedToUpdatePlanReport(planReportId: PlanReportId) : ReportException(
        "Failed to update plan report with id = ${planReportId.id}"
    )


    class FailedToUpdateTaskReport(taskReportId: TaskReportId) : ReportException(
        "Failed to update task report with id = ${taskReportId.id}"
    )

    class FailedToDeletePlanReport(planReportId: PlanReportId) : ReportException(
        "Failed to delete plan report with id = ${planReportId.id}"
    )

    class FailedToDeleteTaskReport(taskReportId: TaskReportId) : ReportException(
        "Failed to delete task report with id = ${taskReportId.id}"
    )

    class PlanReportAlreadyExist(employeeId: ReportEmployeeId,planReportTitle: PlanReportTitle) : ReportException(
        "Plan report with employeeId = ${employeeId.value} and title = ${planReportTitle.value} already exist"
    )

    class TaskReportAlreadyExist(employeeId: ReportEmployeeId,taskReportTitle: TaskReportTitle) : ReportException(
        "Task report with employeeId = ${employeeId.value} and title = ${taskReportTitle.value} already exist"
    )

    class FailedToSavePlanReport(employeeId: ReportEmployeeId, planReportTitle: PlanReportTitle) : ReportException(
        "Failed to save plan report with employeeId = ${employeeId.value} and title = ${planReportTitle.value}"
    )

    class FailedToTaskPlanReport(employeeId: ReportEmployeeId, taskReportTitle: TaskReportTitle) : ReportException(
        "Failed to save task report with employeeId = ${employeeId.value} and title = ${taskReportTitle.value}"
    )



}