package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.dtos.TaskReportDetails
import api.masterplan.app.reportsModule.domain.models.entity.TaskReport
import api.masterplan.app.reportsModule.domain.models.value.*

interface TaskReportService {

    fun getTaskReport(reportId: TaskReportId): TaskReportDetails

    fun updateTaskReport(reportId: TaskReportId, updatedTaskReport: TaskReport): TaskReportId

    fun deleteTaskReport(reportId: TaskReportId): TaskReportId

    fun createTaskReport(id: TaskReportId? = null,title: TaskReportTitle, description: TaskReportDescription? = null,
                         employeeId: ReportEmployeeId, taskId: ReportTaskId, documentId: ReportDocumentId): TaskReportId

    fun getCreatedTaskReports(employeeId: ReportEmployeeId): List<TaskReportDetails>

    fun filterCreatedTaskByStatus(employeeId: ReportEmployeeId, status: ReportStatus): List<TaskReportDetails>

    fun getSubordinatesTaskReports(subordinatesIds:Set<ReportEmployeeId>): List<TaskReportDetails>

    fun filterSubordinatesTaskReportsByStatus(subordinatesIds:Set<ReportEmployeeId>, status: ReportStatus): List<TaskReportDetails>

    fun changeTaskReportStatus(reportId: TaskReportId, status: ReportStatus): TaskReportId

}