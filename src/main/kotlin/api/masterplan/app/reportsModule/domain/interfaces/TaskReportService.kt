package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.models.entity.TaskReport
import api.masterplan.app.reportsModule.domain.models.value.ReportDocumentId
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.ReportTaskId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportDescription
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportStatus
import api.masterplan.app.reportsModule.domain.models.value.TaskReportTitle

interface TaskReportService {

    fun getTaskReport(taskId: TaskReportId): TaskReportDetails

    fun updateTaskReport(taskId: TaskReportId, updatedTaskReport: TaskReport): TaskReportId

    fun deleteTaskReport(taskId: TaskReportId): TaskReportId

    fun createTaskReport(id: TaskReportId? = null,title: TaskReportTitle, description: TaskReportDescription? = null,
                         employeeId: ReportEmployeeId, taskId: ReportTaskId, documentId: ReportDocumentId): TaskReportId

    fun getCreatedTaskReports(employeeId: ReportEmployeeId): List<TaskReportDetails>

    fun filterCreatedTaskByStatus(employeeId: ReportEmployeeId, status: TaskReportStatus): List<TaskReportDetails>

    fun getToCheckTaskReports(directorId: ReportEmployeeId): List<TaskReportDetails>

    fun filterToCheckTaskByStatus(directorId: ReportEmployeeId, status: TaskReportStatus): List<TaskReportDetails>

    fun changeTaskReportStatus(reportId: TaskReportId, status: TaskReportStatus): TaskReportId

}