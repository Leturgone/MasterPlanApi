package api.masterplan.app.reportsModule.domain.dtos

import api.masterplan.app.reportsModule.domain.models.value.*

data class TaskReportDetails(
    val id: TaskReportId,
    val title: TaskReportTitle,
    val creationDate: TaskReportDate,
    val editDate: TaskReportDate? = null,
    val description: TaskReportDescription? = null,
    val reportStatus: ReportStatus,
    val employeeId: ReportEmployeeId,
    val taskId: ReportTaskId,
    val documentId: ReportDocumentId
)