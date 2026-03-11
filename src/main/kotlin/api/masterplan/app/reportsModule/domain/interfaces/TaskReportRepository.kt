package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.models.entity.TaskReport
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportTitle

interface TaskReportRepository {
    fun getTaskReport(taskReportId: TaskReportId): TaskReport?

    fun deleteTaskReport(taskReportId: TaskReportId): TaskReportId?

    fun saveTaskReport(report: TaskReport): TaskReportId?

    fun getTaskReportsByEmployeeId(employeeId: ReportEmployeeId): List<TaskReport>

    fun getTaskReportByEmployeeIds(employeeIds: Set<ReportEmployeeId>): List<TaskReport>

    fun isPlanReportExist(employeeId: ReportEmployeeId,taskReportTitle: TaskReportTitle): Boolean

    fun updatePlanReport(taskReportId: TaskReportId, updatedReport: TaskReport): TaskReportId?
}