package api.masterplan.app.reportsModule.domain.dtos

import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportTaskId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportDate
import api.masterplan.app.reportsModule.domain.models.value.TaskReportDescription
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportStatus
import api.masterplan.app.reportsModule.domain.models.value.TaskReportTitle

data class TaskReportDetails(
    val id: TaskReportId,
    val title: TaskReportTitle,
    val creationDate: TaskReportDate,
    val editDate: TaskReportDate? = null,
    val description: TaskReportDescription? = null,
    val reportStatus: TaskReportStatus,
    val employeeId: ReportEmployeeId,
    val taskId: ReportTaskId,
    val documentId: ReportDocumentId
)