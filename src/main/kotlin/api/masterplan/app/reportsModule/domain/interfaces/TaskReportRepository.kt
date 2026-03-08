package api.masterplan.app.reportsModule.domain.interfaces

import api.masterplan.app.reportsModule.domain.models.entity.TaskReport
import api.masterplan.app.reportsModule.domain.models.value.ReportEmployeeId
import api.masterplan.app.reportsModule.domain.models.value.TaskReportId

interface TaskReportRepository {
    fun getTaskReport(taskReportId: TaskReportId): TaskReport?

    fun deleteTaskReport(taskReportId: TaskReportId): TaskReportId?

    fun savePlanReport(report: TaskReport): TaskReportId?

    fun getPlanReportsByEmployeeId(employeeId: ReportEmployeeId): List<TaskReport>

    fun getTaskReportByEmployeeIds(employeeIds: List<ReportEmployeeId>): List<TaskReport>

    fun isPlanReportExist(taskReportId: TaskReportId): Boolean

    fun updatePlanReport(taskReportId: TaskReportId, updatedReport: TaskReport): TaskReportId
}