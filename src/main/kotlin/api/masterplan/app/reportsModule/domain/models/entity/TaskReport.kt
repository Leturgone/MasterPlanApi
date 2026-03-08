package api.masterplan.app.reportsModule.domain.models.entity

import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportTaskId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportDate
import api.masterplan.app.reportsModule.domain.models.value.TaskReportDescription
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportStatus
import api.masterplan.app.reportsModule.domain.models.value.TaskReportTitle
import java.time.LocalDateTime

@ConsistentCopyVisibility
data class TaskReport private constructor(
    val id: TaskReportId,
    val title: TaskReportTitle,
    val creationDate: TaskReportDate,
    val editDate: TaskReportDate? = null,
    val description: TaskReportDescription? = null,
    val reportStatus: TaskReportStatus,
    val employeeId: ReportEmployeeId,
    val taskId: ReportTaskId,
    val documentId: ReportDocumentId
){
    companion object{
        fun create(id: TaskReportId? = null,title: TaskReportTitle, description: TaskReportDescription? = null,
                   employeeId: ReportEmployeeId, taskId: ReportTaskId, documentId: ReportDocumentId
        ): TaskReport{
            return TaskReport(
                id = id?: TaskReportId.generate(),
                title = title,
                creationDate = TaskReportDate(LocalDateTime.now()),
                editDate = null,
                description = description,
                reportStatus = TaskReportStatus.NOT_CHECKED,
                employeeId = employeeId,
                taskId = taskId,
                documentId = documentId
            )
        }
    }

    fun changeTaskReportStatus(taskReportStatus: TaskReportStatus): TaskReport{
        return this.copy(reportStatus = taskReportStatus)
    }

    fun update(title: TaskReportTitle,description: TaskReportDescription,documentId: ReportDocumentId):TaskReport{
        return this.copy(
            title = title,
            description = description,
            reportStatus = TaskReportStatus.NOT_CHECKED,
            editDate = TaskReportDate(LocalDateTime.now()),
            documentId = documentId, )
    }
}
